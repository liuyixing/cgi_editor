#Cgi接口文档编辑器

PHP项目中使用了XML文件来登记每个Cgi接口的明细信息，包括Cgi名称、参数信息、开发者名称等等。目的是通过代码生成工具在框架层提供统一的Cgi入口参数校验机制，以简化团队成员的开发工作，使开发者更能集中于业务代码的实现。

因为直接编辑XML文件内容的话，很可能会有输入错误，而且格式不够直观。所以，使用Java Swing技术开发了这个简单的编辑器，通过可视化的方式间接编辑XML文件的内容。

最终效果图:

![Cgi编辑器效果图](https://raw.githubusercontent.com/liuyixing/MarkdownPhotos/master/cgi_editor/001.png)