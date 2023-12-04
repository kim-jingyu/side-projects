from pymongo import MongoClient

client = MongoClient('localhost', 27017)
db = client.jungle  # jungle 이라는 이름의  데이터베이스를 만든다.
#
# # insert
#
db.users.insert_one({'name': 'kim', 'age': 20})
# db.users.insert_one({'name': 'lee', 'age': 30})
# db.users.insert_one({'name': 'park', 'age': 40})
#
# # 조회
#
# all_users = list(db.users.find({}))
# user_age_30 = list(db.users.find({'age': 30}))
# print(all_users[0])
# print(all_users[0]['name'])
# for user in all_users:
#     print(user)
#
# user_kim = db.users.find_one({'name': 'kim'})
# print(user_kim)
# # 특정 키를 제외하고 조회하기
# user_lee_except_id = db.users.find_one({'name': 'lee'}, {'_id': False})
# print(user_lee_except_id)
#
#
# # 수정
# # db.people.update_many(찾을조건, { '$set': 어떻게바꿀지 })
#
# db.users.update_one({'name': 'kim'}, {'$set': {'age': 19}})
# user_kim_updated = db.users.find_one({'name': 'kim'})
# print(user_kim_updated)
#
#
# # 삭제
db.users.delete_one({'name':'kim'})
user_kim_deleted = db.users.find_one({'name': 'kim'})
print(user_kim_deleted)

# db.users.delete_many({})