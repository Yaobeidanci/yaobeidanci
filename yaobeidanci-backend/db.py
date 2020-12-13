import sqlite3


# 官方文档中将查询结果以dict的结构返回的方案
def dict_factory(cursor, row):
    d = {}
    for idx, col in enumerate(cursor.description):
        d[col[0]] = row[idx]
    return d


class DBTool:
    # 对sqlite的封装
    def __init__(self):
        self.db = sqlite3.connect('db.sqlite', check_same_thread=False)
        # 将查询结果用dict替代tuple
        self.db.row_factory = dict_factory
        self.c = self.db.cursor()

    def execute(self, sql, args, commit=True):
        # 执行其他sql语句
        try:
            self.c.execute(sql, args)
            if commit:
                self.db.commit()
            return True
        except Exception as ex:
            print(ex)
            return False

    def execute_query(self, sql, args):
        # 执行select
        self.c.execute(sql, args)
        return self.c.fetchall()

    def reset(self):
        # 删库重建
        with open('create.sql') as f:
            self.c.executescript(f.read())
            self.db.commit()

    def commit(self):
        # 手动commit
        self.db.commit()

    def close(self):
        # 关闭数据库
        self.db.close()


if __name__ == '__main__':
    db = sqlite3.connect('test.sqlite')
    c = db.cursor()
    x = c.execute("select * from user")
    print(c.fetchall())
    db.close()
