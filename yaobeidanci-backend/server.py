from flask import Flask, request, send_file
import requests
import io
import data_fetcher

app = Flask(__name__)


@app.route('/api/wordList')
def word_list():
    # 请求单词列表，total为单词个数，从文件中读取而不是数据库
    try:
        total = request.args.get('total')
        return {
            'status': 200,
            'data': data_fetcher.fetch_word('cet4', int(total))
        }
    except:
        return {
            'status': 404
        }


@app.route('/api/sound')
def word_sound():
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


@app.route('/api/dict')
def word_dict():
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
    app.run()
