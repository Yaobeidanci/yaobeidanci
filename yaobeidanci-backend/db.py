import sqlite3
import threading

# 需要加锁，否则sqlite会报错Recursive use of cursors not allowed
lock = threading.Lock()


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
            lock.acquire(True)
            self.c.execute(sql, args)
            if commit:
                self.db.commit()
            return True
        except Exception as ex:
            print(ex)
            return False
        finally:
            lock.release()

    def execute_query(self, sql, args):
        # 执行select
        try:
            lock.acquire(True)
            self.c.execute(sql, args)
            return self.c.fetchall()
        finally:
            lock.release()

    def reset(self):
        # 删库重建
        users = self.execute_query("select uid from user", ())
        for user in users:
            self.execute("drop table [" + user['uid'] + "_table]", ())
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
