from flask import Flask, request, send_file
import requests
import io
import data_fetcher
from db import DBTool
import util

app = Flask(__name__)
db = DBTool()
# 注意，这里会清空数据库
data_fetcher.load_book_list()
# db.reset()

# 设置响应使用utf-8编码而不是unicode
app.config['JSON_AS_ASCII'] = False


@app.route('/api/login', methods=['POST'])
def login():
    # 登录，字段为username和password，返回uid，使用/resource/user路由获取用户信息
    username = request.form.get('username')
    password = request.form.get('password')

    if util.validate([username, password]):
        res = db.execute_query("select * from user where username='{}'".format(username))
        if len(res) == 1:
            if res[0]['password'] == password:
                return {
                    'status': 200,
                    'msg': '登陆成功',
                    'data': {
                        'uid': res[0]['uid']
                    }
                }
            else:
                return {
                    'status': 404,
                    'msg': '密码错误'
                }
        elif len(res) == 0:
            return {
                'status': 404,
                'msg': '用户名不存在'
            }
        else:
            return {
                'status': 404,
                'msg': '未知错误'
            }
    else:
        return {
            'status': 404,
            'msg': '字段格式错误'
        }


@app.route('/api/register', methods=['POST'])
def register():
    # 注册，字段为username、password、phone
    username = request.form.get('username')
    password = request.form.get('password')
    phone = request.form.get('phone')
    if util.validate([username, password, phone]):
        res = db.execute_query("select * from user where username='{}'".format(username))
        if len(res) == 0:
            # uid其实就是username的hash
            res = db.execute("insert into user values ('{}','{}','{}','{}','{}','{}')"
                             .format(username, password, hash(username), phone, '', ''))
            if res:
                return {
                    'status': 200,
                    'msg': '注册成功'
                }
            else:
                return {
                    'status': 404,
                    'msg': '未知错误'
                }
        else:
            return {
                'status': 404,
                'msg': '用户已存在'
            }
    else:
        return {
            'status': 404,
            'msg': '字段格式错误'
        }


# TODO 没做
@app.route('/api/setBook')
def set_book():
    # 设置单词书，字段为uid和book_id
    uid = request.args.get('uid')
    book_id = request.args.get('book_id')
    return {
        'status': 200,
        'msg': '操作成功'
    }


# TODO 没做
@app.route('/api/addStarWord')
def add_star_word():
    # 设置单词书，字段为uid和
    uid = request.args.get('uid')
    return {
        'status': 200,
        'msg': '操作成功'
    }


# TODO 没做
@app.route('/api/addStarSentence')
def add_star_sentence():
    # 设置单词书，字段为uid和
    uid = request.args.get('uid')
    return {
        'status': 200,
        'msg': '操作成功'
    }


@app.route('/resource/user')
def get_user():
    # 获取用户信息，字段为uid
    uid = request.args.get('uid')
    if util.validate([uid]):
        res = db.execute_query("select * from user where uid='{}'".format(uid))
        if len(res) == 1:
            return {
                'status': 200,
                'msg': '查询成功',
                'data': {
                    'username': res[0]['username'],
                    'phone': res[0]['phone'],
                    'nickname': res[0]['nickname']
                }
            }
        else:
            return {
                'status': 404,
                'msg': '未知错误'
            }
    else:
        return {
            'status': 404,
            'msg': '字段格式错误'
        }


# TODO 目前是假数据
@app.route('/resource/starWords')
def get_star_words():
    # 获取收藏的单词，字段为uid
    uid = request.args.get('uid')
    return {
        'status': 200,
        'data': data_fetcher.fetch_word('CET4luan_2', 5)
    }


# TODO 目前是假数据
@app.route('/resource/starSentences')
def get_star_sentences():
    # 获取收藏的例句，字段为uid
    uid = request.args.get('uid')
    sentences = list()
    sentences.append({
        'sentence': 'example sentence',
        'explain': '例句'
    })
    sentences.append({
        'sentence': 'example sentence',
        'explain': '例句'
    })
    return {
        'status': 200,
        'data': sentences
    }


@app.route('/resource/bookList')
def get_book_list():
    # 获取单词书列表
    res = db.execute_query("select * from book")
    return {
        'status': 200,
        'data': res
    }


# TODO 目前是从json文件读取
@app.route('/resource/wordList')
def get_word_list():
    # 获取单词列表，total为单词个数，从文件中读取而不是数据库
    try:
        book_id = request.args.get('book_id')
        total = request.args.get('total')
        return {
            'status': 200,
            'data': data_fetcher.fetch_word(book_id, int(total))
        }
    except:
        return {
            'status': 404
        }


@app.route('/resource/voice')
def get_word_voice():
    # 请求有道单词发音，word为请求的单词，type美音为0，英音为1
    try:
        word_arg = request.args.get('word')
        type_arg = request.args.get('type')
        data = requests.get('http://dict.youdao.com/dictvoice?type={}&audio={}'.format(type_arg, word_arg))
        return send_file(io.BytesIO(data.content),
                         attachment_filename='logo.mp3',
                         mimetype='audio/mp3')
    except:
        return {
            'status': 404
        }


# TODO 请求待优化
@app.route('/resource/dict')
def get_word_dict():
    # 使用网上的非官方有道接口，还需要调整
    # 传入word为需要查的词
    try:
        url = 'http://dict.youdao.com/jsonapi?jsonversion=2&client=mobile&q={}&dicts=%7B%22count%22%3A99%2C%22dicts%22%3A%5B%5B%22ec%22%2C%22ce%22%2C%22newcj%22%2C%22newjc%22%2C%22kc%22%2C%22ck%22%2C%22fc%22%2C%22cf%22%2C%22multle%22%2C%22jtj%22%2C%22pic_dict%22%2C%22tc%22%2C%22ct%22%2C%22typos%22%2C%22special%22%2C%22tcb%22%2C%22baike%22%2C%22lang%22%2C%22simple%22%2C%22wordform%22%2C%22exam_dict%22%2C%22ctc%22%2C%22web_search%22%2C%22auth_sents_part%22%2C%22ec21%22%2C%22phrs%22%2C%22input%22%2C%22wikipedia_digest%22%2C%22ee%22%2C%22collins%22%2C%22ugc%22%2C%22media_sents_part%22%2C%22syno%22%2C%22rel_word%22%2C%22longman%22%2C%22ce_new%22%2C%22le%22%2C%22newcj_sents%22%2C%22blng_sents_part%22%2C%22hh%22%5D%2C%5B%22ugc%22%5D%2C%5B%22longman%22%5D%2C%5B%22newjc%22%5D%2C%5B%22newcj%22%5D%2C%5B%22web_trans%22%5D%2C%5B%22fanyi%22%5D%5D%7D&keyfrom=mdict.7.2.0.android&model=honor&mid=5.6.1&imei=659135764921685&vendor=wandoujia&screen=1080x1800&ssid=superman&network=wifi&abtest=2&xmlVersion=5.1'
        word = request.args.get('word')
        data = requests.get(url.format(word))
        json_data = data.json()
        return {
            'status': 200,
            'data': {
                'word': json_data['input'],
                'explain': json_data['ec']['word'],
                'phrases': json_data['phrs'],
            }
        }
    except:
        return {
            'status': 404
        }


if __name__ == '__main__':
    app.run(host='0.0.0.0')
