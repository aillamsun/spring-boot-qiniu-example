# spring-boot-qiniu-example
spring boot  七牛云上传图片


### 配置 qiniu.properties

```java
qiniu.access-key = 
qiniu.secret-key = 
qiniu.bucket = 
qiniu.bucket-url = 

```

### Controller 
```java

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String uploadImg(HttpServletRequest request) throws Exception {
        String filePath = "";
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                String fileName = file.getOriginalFilename();
                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
                DefaultPutRet putRet = upload(file.getBytes(), newFileName);
                filePath += qiNiuProperties.getBucketUrl() + putRet.key + ",";
            }
            if (filePath.endsWith(",")) {
                filePath = filePath.substring(0, filePath.length() - 1);
            }
        }
        return filePath;
    }

```


### Test 测试上传
```java
private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;


    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).alwaysExpect(status().isOk()).addFilters(new CharacterEncodingFilter()).build();
    }


    @Test
    public void testUpload() throws Exception {
        String uri = "/qiniu/upload";
        File f = new File("/Users/sungang/Pictures/timg2.jpeg");
        FileInputStream fis = new FileInputStream(f);
        MockMultipartFile upload = new MockMultipartFile("upload", f.getName(), "multipart/form-data", fis);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.fileUpload(uri).file(upload).accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();

        Assert.assertEquals(status, 200);
        Assert.assertNotNull(content);

        System.out.println(status);
        System.out.println(content);
    }

```

输出结果：
200
http://opbek976n.bkt.clouddn.com/20170601135932_341.jpeg
