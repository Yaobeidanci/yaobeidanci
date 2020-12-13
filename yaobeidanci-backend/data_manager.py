import json
import os
from db import DBTool
import hashlib
import random


root = 'res/'
origins = ['新概念英语第一册', '新概念英语第二册', '新概念英语第三册', '新概念英语第四册', '新东方英语']


def fetch_word(book_id, num):
    # 从json文件中读取开头num个单词数据并结构化返回
    filename = root + book_id + '.json'

    data_list = []
    file = open(filename, 'r', encoding='utf-8')
    index = 0
    print(filename)
    for line in file.readlines():
        word_obj = get_word(line)
        data_list.append(word_obj)
        index = index + 1
        if index >= num:
            break

    file.close()
    return data_list


# 初始化book表
def load_book_list():
    with open(root + 'books.json', encoding='utf-8') as f:
        obj = json.loads(f.read())
        db = DBTool()
        for o in obj:
            db.execute("insert into book values (?, ?, ?, ?, ?)",
                       (o['id'], o['name'], o['size'], o['num'], 'img_' + o['id'] + '.jpg'), commit=False)
        db.commit()
        db.close()


# 从指定json加载单词
def get_word(raw_word_json):
    words = raw_word_json.strip()
    word_json = json.loads(words)
    word = word_json['headWord']
    word_id = word_json['wordRank']
    category = word_json['bookId']

    content = word_json['content']['word']['content']

    # 记忆方法
    remember_method_raw = content['remMethod'] if 'remMethod' in content else None
    if remember_method_raw is not None:
        remember_method = remember_method_raw['val']
    else:
        remember_method = None

    # 处理例句
    sentences_raw = content['sentence'] if 'sentence' in content else None

    sentences = []
    if sentences_raw is not None:
        for i in sentences_raw['sentences']:
            sentences.append({
                'sentence_id': word + '_' + str(len(sentences)),
                'sentence': i['sContent'],
                'translation': i['sCn'],
                'origin_word': word
            })

    # 音标
    phonetic_uk = content['ukphone'] if 'ukphone' in content else None
    phonetic_us = content['usphone'] if 'usphone' in content else None

    # 处理短语
    phrases_raw = content['phrase'] if 'phrase' in content else None
    phrases = []
    if phrases_raw is not None:
        for i in phrases_raw['phrases']:
            phrases.append({
                'phrase': i['pContent'],
                'explain': i['pCn']
            })

    # 处理单词释义
    explains_raw = content['trans'] if 'trans' in content else None
    explains = []
    if explains_raw is not None:
        for i in explains_raw:
            explain_item = {
                'prop': i['pos'] if 'pos' in i else None,
                'explain_c': i['tranCn'] if 'tranCn' in i else None,
                'explain_e': i['tranOther'] if 'tranOther' in i else None
            }
            explains.append(explain_item)

    # 处理相关词汇
    relate_words_raw = content['relWord'] if 'relWord' in content else None
    relate_words = []
    if relate_words_raw is not None:

        for i in relate_words_raw['rels']:
            for j in i['words']:
                relate_word_item = {
                    'prop': i['pos'] if 'pos' in i else None,
                    'word': j['hwd'] if 'hwd' in j else None,
                    'explain': j['tran'] if 'tran' in j else None
                }
                relate_words.append(relate_word_item)

    word_obj = {
        'word_id': word_id,
        'word': word,
        'category': category,
        'phonetic_uk': phonetic_uk,
        'phonetic_us': phonetic_us,
        'sentences': sentences,
        'sentences_label': '例句',
        'relate_words': relate_words,
        'relate_words_label': '相关词',
        'explains': explains,
        'phrases': phrases,
        'phrases_label': '短语',
        'remember_method': remember_method,
        'remember_method_label': '记忆方法'
    }
    return word_obj


# 从数据库中的记录恢复word对象
def get_word_from_db_form(word_obj, db):
    word_obj['explains'] = json.loads(word_obj['explains'])
    word_obj['relate_words'] = json.loads(word_obj['relate_words'])
    word_obj['phrases'] = json.loads(word_obj['phrases'])
    word_obj['remember_method'] = json.loads(word_obj['remember_method'])
    word_obj['questions'] = json.loads(word_obj['questions'])
    # 附加上label
    word_obj['sentences_label'] = '例句'
    word_obj['relate_words_label'] = '相关词'
    word_obj['phrases_label'] = '短语'
    word_obj['remember_method_label'] = '记忆方法'

    sentences = db.execute_query("select distinct * from sentence where origin_word=?", (word_obj['word'],))
    word_obj['sentences'] = sentences
    return word_obj


# 初始化word、sentence表
def load_word_list():
    db = DBTool()
    res = db.execute_query("select book_id from book", ())
    books = [i['book_id'] for i in res]
    print(books)
    for book in books:
        with open(root + book + '.json', 'r', encoding='utf-8') as file:
            lines = file.readlines()
            for line in lines:
                word_obj = get_word(line)

                # 生成四选一题目
                questions = []
                question_indexes = random.sample([i for i in range(len(lines))], 3)
                for question_index in question_indexes:
                    quest_obj = get_word(lines[question_index])
                    questions.append(quest_obj['explains'][0])
                questions.append(word_obj['explains'][0])
                questions = random.shuffle(questions)
                word_obj['questions'] = questions
                # question_indexes = [i for i in range(len(lines))]

                print(word_obj['word_id'])
                sentences = word_obj['sentences']
                for sentence in sentences:
                    rand = random.randint(0, 2)
                    db.execute("insert into sentence values (?,?,?,?,?,?)",
                               (sentence['sentence_id'], sentence['sentence'], sentence['translation'],
                                sentence['origin_word'], rand, origins[rand]), commit=False)
                db.execute("insert into word values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                           (word_obj['word_id'], word_obj['word'], word_obj['category'], word_obj['phonetic_uk'],
                            word_obj['phonetic_us'], json.dumps(word_obj['relate_words']),
                            json.dumps(word_obj['explains']), json.dumps(word_obj['phrases']),
                            json.dumps(word_obj['remember_method']), json.dumps(word_obj['questions'])), commit=False)

            db.commit()
    db.close()


# 背单词状态机，获取用户需要背哪个单词
def word_generator(uid, db):
    res = db.execute_query("select * from schedule where uid=?", (uid,))
    book = res[0]['book']
    current_progress = res[0]['current_progress']
    return book, current_progress


if __name__ == '__main__':
    print('start')
    db = DBTool()
    db.reset()
    db.close()
    load_book_list()
    load_word_list()
    # x = hashlib.md5()
    # x.update('123456789'.encode("utf-8"))
    # print(x.hexdigest())
    # print(random.sample([i for i in range(1000)], 100))
