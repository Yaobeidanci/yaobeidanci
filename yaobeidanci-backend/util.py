import re
import hashlib

rule = re.compile('[;\s]')


def validate(field_list):
    # 判断字段是否合法，即非空、无非法字符，防SQL注入
    for field in field_list:
        if field is None or field == '' or rule.search(field):
            return False
    return True


def get_md5(src):
    x = hashlib.md5()
    x.update(src.encode("utf-8"))
    return x.hexdigest()
