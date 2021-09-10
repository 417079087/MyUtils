# MyUtils
健康码参数截取使用示例：
```java
var qrCode:String? = ""
qrCode = if (content.startsWith("http")){
            //UrlUtil.getUrl("https://h5.dingtalk.com/healthAct/index.html?qrCode=V229495d37a6c75d794b87198b160b526b#/result","qrCode")
            var params:MutableMap<String,List<String>> = UrlUtil.getQueryParams(content)
            qrCode = params?.get("qrCode")?.get(0) ?:""
            qrCode?.replace("#/result", "")


      }  else {
            content
      }
```
