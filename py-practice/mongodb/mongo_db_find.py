from bson import ObjectId
from pymongo import MongoClient

client = MongoClient('localhost', 27017)
db = client.daummovie

# id 필드를 제외하고 모든 값 조회하기
# all_users_ex_id = list(db.users.find({}, {'_id': False}))
# for user in all_users_ex_id:
#     print(user)
#
# print('---------------------------------------------------------------------------------')
#
# # 특정 필드 조회하기
# age_20_user = db.users.find_one({'age': 20}, {'_id': False})
# print(age_20_user)
#
# print('---------------------------------------------------------------------------------')
#
# # 모든 값 조회 후, 결과 골라서 보기
# all_users = list(db.users.find({}))
# first_user = all_users[0]
# name_of_second_user = all_users[1]['name']
# print("first_user:",first_user,", second_user's name:", name_of_second_user)

# 테스트
find_movie = db.movies.find_one({'_id': ObjectId("64d4dfc8bfa0bd2ba40cc968")})
print(find_movie)
