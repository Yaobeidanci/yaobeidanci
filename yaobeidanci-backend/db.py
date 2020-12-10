import sqlite3


class DBTool:
    # 对sqlite的封装
    def __init__(self):
        self.db = sqlite3.connect('db.sqlite', check_same_thread=False)
        self.c = self.db.cursor()

    def execute(self, sql):
        # 执行其他sql语句
        try:
            self.c.execute(sql)
            self.db.commit()
            return True
        except:
            return False

    def execute_query(self, sql):
        # 执行select
        self.c.execute(sql)
        return self.c.fetchall()

    def reset(self):
        # 删库重建
        with open('create.sql') as f:
            self.c.executescript(f.read())
            self.db.commit()


if __name__ == '__main__':
    db = sqlite3.connect('test.sqlite')
    c = db.cursor()
    x = c.execute("select * from user")
    print(c.fetchall())
    db.close()
