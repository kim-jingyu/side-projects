import re

# . (ca.e) -> 하나의 문자를 의미함
# ^ (^de) -> 문자열의 시작
# $ (se$) -> 문자열의 끝
p = re.compile("ca.e")


def print_match(param):
    if param:
        print("param.group() :", param.group())  # group -> 일치하는 문자열 반환
        print("param.string :", param.string)  # string -> 입력받은 문자열
        print("param.start() :", param.start())  # 일치하는 문자열의 시작 index
        print("param.end() :", param.end())  # 일치하는 문자열의 끝 index
        print("param.span() :", param.span())  # 일치하는 문자열의 시작, 끝 index
    else:
        print("매칭되지 않았습니다.")


# match : 주어진 문자열의 처음부터 일치하는지 확인
m = p.match("caffe")
print_match(m)

# search : 주어진 문자열 중에 일치하는게 있는지 확인
s = p.search("good care")
print_match(s)

# findall : 일치하는 모든 것을 리스트 형태로 반환
lst = p.findall("good care cafe")
print(lst)