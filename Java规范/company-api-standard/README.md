    本文档用于指导API接口设计者和使用者更好的设计使用API接口。此标准参考现行REST API设计标准。
一、接口url标准
    1.【强制】设计接口路径要基于业务领域的数据建模，而非基于功能建模。
        正例：接口取得所有的dog信息--GET（method） /api/dog。路径中只体现了数据资源是dog。  
        反例：接口取得所有的dog信息--/getAllDogs。路径中反应了一个获取所有dog的功能操作。
        原因：基于功能建模设计的url会大量出现反复冗余的url，这样就需要记忆大量的url，而且这种url的语义缺乏统一性。
    2.【强制】使用HTTP的标准方法（method）作为操作数据的基本语义。
        正例：示例中已说明每种HTTP的mehod语义
        获取dog
          url--/api/dog method--GET
        创建dog
          url--/api/dog method--POST
        更改dog
          url--/api/dog method--PUT
        删除dog
          url--/api/dog method--DELETE
        修改部分dog信息
          url--/api/dog method--PATCH
     3.【强制】url设计中只使用名词，避免动词，url中使用动词，意味着你是在对功能建模，而非数据。
     4.【推荐】url大多情况下使用英文命名，为了语义清晰推荐使用单数。区分批量操作通过url参数而非名词复数。参见url参数说明部分。
     5.【推荐】要有统一的切入点。原则上说，一个api应该有一个root path '/',
	 其返回一个url map，包括了所有的resouces所对应的url,这样客户端更容易去发现和使用api。
	 如有特殊情况，比如针对不同平台提供API，可提供针对不同平台的root paht,例：/mobile/user/xxx,/web/user/xxxx
     6.【强制】要在url中表达资源间的关系。比如GET /persons/1/dogs 返回所有属于person 1的狗。这种模式可以表述为：
        /{relationship-name}[/{resource-id}]/…/{relationship-name}[/{resource-id}]
     7.【强制】url命名全部使用小写，多个名词之间使用中线（-）分割。
二、请求参数设计标准
       请求参数分为：PathVariable RequestParam RequestBody
     1.【强制】PathVariable类型的参数统一使用明确的唯一标识。例如api/dogs/{id} 中的{id}如何表达，是类似于‘/dogs/1’，还是‘/dogs/haha’。
	 如在此接口下已经使用主键作为PathVariable参数，那么就不能再使用name作为PathVariable参数，
	 推荐使用资源的ID唯一标识作为PathVariable参数，通常为数据库表的主键。
     2.【推荐】支持条件查询。举例：
     /module/tickets?state=open GET 过滤
     /module/tickets?sort=-priority GET 排序
     /module/tickets?sort=-priority,created_at GET 排序
     /module/tickets?sort=-updated_at GET 排序
     /module/tickets?state=closed&sort=-updated_at GET 过滤+排序
     /module/tickets?q=return&state=open&sort=-priority,created_at GET 搜索+过滤+排序
     /module/tickets/recently_closed GET 一般数据请求
     /module/tickets?fields=id,subject,customer_name,updated_at&state=open&sort=-updated_at GET 指定返回列
     /cars?offset=10&limit=5 GET 分页
     3.【强制】对于查询条件参数，参数中的&符号代表不同查询参数之间是‘与(and)’的关系。
	 如需设计条件‘或(or)’的关系可使用'in={param_name:param_value}'或'like={param_name:param_value}'。
	 其实实际使用中，表达‘或(or)’的查询条件很少使用。
     4.【强制】参数的命名要准确。比如反应"消息"应使用message 错误的使用notice。
     5.【强制】参数命名统一使用下划线（蛇形命名）分割多个单词。
     6.【强制】参数名中的中文拼音要使用被中国人创造出来，没有英文翻译的。比如：淘宝使用taobao，反例：微信使用weixin，正确使用wechat。
     7.【强制】对于PUT或POST方法的接口参数，请求体（RequestBody）中存放json格式的参数代表要更新或添加的字段。使用PathVariable RequestParam作为更新条件。
     8.【推荐】字符集使用utf-8
三、响应结果设计标准
     1.【强制】响应结果统一使用json数据格式
     2.【推荐】字符集使用utf-8
     3.【强制】json字段中不得出现null true false,如果出现这些名词都应为字符类型 
	 反例：{"obj":null,"is_checked":true} ,
	 正例：{"obj":"null","ischecked":"true"}
     4.【强制】不要在响应结果中不要直接封装http code。比如：400，404
     5.【推荐】如果响应结果中有类似状态码字段，不要使用晦涩难懂的数字，请使用语义清晰的文字。
	 正例：{"code":"login_username_invalid","message":"用户名无效"}
四、认证和授权
     1.【推荐】使用OAuth2或简单token鉴权。
五、版本
     1.【推荐】使用在Http Accept Header中添加版本信息的方式存储版本信息。不推荐在url中存放版本信息,因为url应该为稳定易懂，而版本信息是持续迭代多变的。
六、生成API文档
     1.【推荐】推荐使用swagger生成文档，如使用spring技术栈可也可使用spring-rest-docs。
        说明：使用spring技术栈如选用springfox的swagger框架生成文档，会因为controller代码中大量写入注解造成代码侵入性过强的问题。
		使用spring-rest-docs框架生成文档，需要配合测试用例书写文档。实际使用中开发团队可酌情使用两种生成文档的方式。
