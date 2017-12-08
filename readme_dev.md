# 这是给开发者看的

- 每个php文件中的`$GLOBALS['response']`变量用来保存结果，并以json格式输出


## response格式

- `response['result']` 结果
- `response['data']` 返回请求的数据
- `response['error]` 错误信息, 通常是错误编号
- `response['message']` 消息

### result值列表

- 1 成功
- 0 失败
- -1 数据库异常
- -10 其他异常
