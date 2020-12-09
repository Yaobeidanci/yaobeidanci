from flask import Flask, request, send_file
import requests
import io
from src import data_fetcher

app = Flask(__name__)


@app.route('/api/wordList')
def word_list():
    # 请求单词列表，total为单词个数，从文件中读取而不是数据库
    total = request.args.get('total')
    return {
        'status': 200,
        'data': data_fetcher.fetch_word('cet4', int(total))
    }


@app.route('/api/sound')
def word_sound():
    # 请求有道单词发音，word为请求的单词，type美音为0，英音为1
    word_arg = request.args.get('word')
    type_arg = request.args.get('type')
    data = requests.get('http://dict.youdao.com/dictvoice?type={}&audio={}'.format(type_arg, word_arg))
    return send_file(io.BytesIO(data.content),
                     attachment_filename='logo.mp3',
                     mimetype='audio/mp3')


if __name__ == '__main__':
    app.run()
