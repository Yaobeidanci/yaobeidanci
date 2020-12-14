from flask import Flask, request, send_file
import requests
import io
import data_manager
from db import DBTool
import util

app = Flask(__name__)
db = DBTool()

# 设置响应使用utf-8编码而不是unicode
app.config['JSON_AS_ASCII'] = False


# 登录，字段为username和password，返回uid，使用/resource/user路由获取用户信息
@app.route('/api/login', methods=['POST'])
def login():
    username = request.form.get('username')
    password = request.form.get('password')

    if util.validate([username, password]):
        res = db.execute_query("select * from user where username=?", (username,))
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


# 注册，字段为username、password、phone
@app.route('/api/register', methods=['POST'])
def register():
    username = request.form.get('username')
    password = request.form.get('password')
    phone = request.form.get('phone')
    if util.validate([username, password, phone]):
        res = db.execute_query("select * from user where username=?", (username,))
        if len(res) == 0:
            # uid其实就是username的hash
            res = db.execute("insert into user values (?, ?, ?, ?, ?, ?)",
                             (username, password, util.get_md5(username), phone, '', ''))
            # res1 = db.execute("create table ? (" +
            #                   "word varchar(50)," +
            #                   "level integer)", (username+"_table",))
            res1 = True
            if res and res1:
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


# 设置单词书，字段为uid和book_id
@app.route('/api/setSchedule')
def set_schedule():
    uid = request.args.get('uid')
    book_id = request.args.get('book_id')
    num_daily = request.args.get('num_daily')
    res = db.execute("insert into schedule values (?,?,?,?,?)", (uid, book_id, "2020-12-13", 1, num_daily))
    if res:
        return {
            'status': 200,
            'msg': '操作成功'
        }
    else:
        return {
            'status': 404,
            'msg': '操作失败'
        }


# 获取计划信息
@app.route('/resoucre/schedule')
def get_schedule():
    uid = request.args.get('uid')
    res = db.execute_query("select * from schedule where uid=?", (uid,))
    if len(res) == 0:
        return {
            'status': 404,
            'msg': '未添加计划'
        }
    return {
        'status': 200,
        'data': res[0]
    }


# 添加单词收藏，字段为uid、word
@app.route('/api/addStarWord')
def add_star_word():
    uid = request.args.get('uid')
    word = request.args.get('word')
    res = db.execute("insert into star_word values (?,?)", (uid, word))
    if res:
        return {
            'status': 200,
            'msg': '操作成功'
        }
    else:
        return {
            'status': 404,
            'msg': '操作失败'
        }


# 添加例句收藏，字段为uid和sentence_id
@app.route('/api/addStarSentence')
def add_star_sentence():
    uid = request.args.get('uid')
    sentence_id = request.args.get('sentence_id')
    res = db.execute("insert into star_sentence values (?,?)", (uid, sentence_id))
    if res:
        return {
            'status': 200,
            'msg': '操作成功'
        }
    else:
        return {
            'status': 404,
            'msg': '操作失败'
        }


# 获取用户信息，字段为uid
@app.route('/resource/user')
def get_user():
    uid = request.args.get('uid')
    if util.validate([uid]):
        res = db.execute_query("select * from user where uid=?", (uid,))
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


# 获取收藏的单词，字段为uid
@app.route('/resource/starWords')
def get_star_words():
    uid = request.args.get('uid')
    res = db.execute_query("select * from word where word in (select word from star_word where uid=?)", (uid,))
    res = [data_manager.get_word_from_db_form(i, db) for i in res]
    print(res)
    return {
        'status': 200,
        'data': res
    }


# 获取收藏的例句，字段为uid
@app.route('/resource/starSentences')
def get_star_sentences():
    uid = request.args.get('uid')
    res = db.execute_query(
        "select * from sentence where sentence_id=(select sentence_id from star_sentence where uid=?)", (uid,))
    print(res)
    return {
        'status': 200,
        'data': res
    }


# 获取单词书列表
@app.route('/resource/bookList')
def get_book_list():
    res = db.execute_query("select * from book", ())
    return {
        'status': 200,
        'data': res
    }


# 获取单词列表，total为单词个数
@app.route('/resource/wordList')
def get_word_list():
    try:
        book_id = request.args.get('book_id')
        total = int(request.args.get('total'))
        res = db.execute_query("select * from word where category=? limit ?", (book_id, total))
        res = [data_manager.get_word_from_db_form(i, db) for i in res]

        return {
            'status': 200,
            'data': res
        }
    except Exception as ex:
        print(ex)
        return {
            'status': 404
        }


# 获取要背的单词，传入uid
@app.route('/resource/word')
def get_current_word():
    # try:
        uid = request.args.get('uid')
        book, next_word_index = data_manager.word_generator(uid, db)
        res = db.execute_query("select * from word where category=? and word_id=?", (book, next_word_index))
        res = data_manager.get_word_from_db_form(res[0], db)
        db.execute("update schedule set current_progress=current_progress + 1", ())
        return {
            'status': 200,
            'data': res
        }
    # except Exception as ex:
    #     print(ex)
    #     return {
    #         'status': 404
    #     }


# 接受用户点击结果
@app.route('/api/setResult')
def set_result():
    uid = request.args.get('uid')
    word_id = request.args.get('word_id')
    mode = request.args.get('mode')
    result = request.args.get('result')
    print(uid, word_id, mode, result)
    return {
        'status': 200,
        'msg': '操作成功'
    }


# 请求有道单词发音，word为请求的单词，type美音为0，英音为1
@app.route('/resource/voice')
def get_word_voice():
    try:
        word_arg = request.args.get('word')
        type_arg = request.args.get('type')
        data = requests.get('http://dict.youdao.com/dictvoice?type={}&audio={}'.format(type_arg, word_arg))
        return send_file(io.BytesIO(data.content),
                         attachment_filename='logo.mp3',
                         mimetype='audio/mp3')
    except Exception as ex:
        print(ex)
        return {
            'status': 404
        }


# TODO 请求待优化
# 使用网上的非官方有道接口，还需要调整
# 传入word为需要查的词
@app.route('/resource/dict')
def get_word_dict():
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
    except Exception as ex:
        print(ex)
        return {
            'status': 404
        }


if __name__ == '__main__':
    app.run(host='0.0.0.0')
