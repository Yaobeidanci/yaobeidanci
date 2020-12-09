import json


def fetch_word(book, num):
    # 从json文件中读取开头num个单词数据并结构化返回
    if book == 'cet4':
        # filename = "CET4luan_1.json"
        filename = 'KaoYan_2.json'
    else:
        filename = "CET6luan_1.json"
    # filename = 'KaoYan_2.json'

    data_list = []
    file = open(filename, 'r', encoding='utf-8')
    index = 0
    print(filename)
    for line in file.readlines():
        words = line.strip()
        word_json = json.loads(words)
        word = word_json['headWord']
        category = word_json['bookId']

        content = word_json['content']['word']['content']
        # 记忆方法
        remember_method = content['remMethod'] if 'remMethod' in content else None
        if remember_method is not None:
            remember_method_obj = {
                'label': remember_method['val'],
                'val': remember_method['val']
            }
        else:
            remember_method_obj = None

        # 处理例句
        sentence = content['sentence']
        sentences = []
        for i in sentence['sentences']:
            sentences.append({
                'sentence': i['sContent'],
                'explain': i['sCn']
            })
        sentence_obj = {
            'label': sentence['desc'],
            'sentences': sentences
        }

        # 音标
        phonetic_uk = content['ukphone']
        phonetic_us = content['usphone']

        # 处理短语
        phrase = content['phrase']
        phrases = []
        for i in phrase['phrases']:
            phrases.append({
                'phrase': i['pContent'],
                'explain': i['pCn']
            })
        phrase_obj = {
            'label': phrase['desc'],
            'phrases': phrases
        }

        # 处理单词释义
        explain = content['trans']
        explain_list = []
        for i in explain:
            explain_obj = {
                'prop': i['pos'],
                'explain_c': i['tranCn'] if 'tranCn' in i else None,
                'explain_e': i['tranOther'] if 'tranOther' in i else None
            }
            explain_list.append(explain_obj)

        # 处理相关词汇
        # print([xx for xx in content])
        relate_word = content['relWord'] if 'relWord' in content else None
        if relate_word is not None:
            relate_words = []
            for i in relate_word['rels']:
                for j in i['words']:
                    relate_word_item = {
                        'prop': i['pos'],
                        'word': j['hwd'],
                        'explain': j['tran']
                    }
                    relate_words.append(relate_word_item)

            relate_word_obj = {
                'label': relate_word['desc'],
                'relateWords': relate_words
            }
        else:
            relate_word_obj = None

        # print(relateWordObj)
        data_list.append({
            'word': word,
            'phonetic_uk': phonetic_uk,
            'phonetic_us': phonetic_us,
            'category': category,
            'sentences': sentence_obj,
            'relate_words': relate_word_obj,
            'explain_list': explain_list,
            'phrases': phrase_obj,
            'remember_method': remember_method_obj
        })
        index = index + 1
        if index >= num:
            break

    file.close()
    return data_list


if __name__ == '__main__':
    print(fetch_word('cet4', 1))
