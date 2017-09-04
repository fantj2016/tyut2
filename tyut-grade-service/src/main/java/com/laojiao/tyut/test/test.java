package com.laojiao.tyut.test;

import com.tyut.eureka.core.pojo.ClassTable;
import com.tyut.eureka.core.pojo.HelpStudy;
import com.tyut.eureka.core.pojo.Notice;
import com.tyut.eureka.core.util.GetCookieUtil;
import com.tyut.eureka.core.util.SendGet;
import com.tyut.eureka.core.util.SendPost;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SuperJohn on 2017/8/19.
 */
public class test {
    @Test
    public void hello(){
        System.out.println("hello");
    }
    @Test//notice list
    public void notice() throws IOException {
        GetCookieUtil getCookieUtil = new GetCookieUtil();
        String cookie = getCookieUtil.getCookieFromNotice();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://jwc.tyut.edu.cn/detail.asp?bigid=7");
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("Cache-Control", "max-age=0");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Cookie", cookie);
        httpGet.setHeader("Host", "jwc.tyut.edu.cn");
        httpGet.setHeader("Referer", "http://jwc.tyut.edu.cn/");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");

        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        String result = EntityUtils.toString(httpResponse.getEntity(), "gb2312");
        Document doc = Jsoup.parse(result);
        org.jsoup.select.Elements tables = doc.select(".drlrimess tbody table");
        System.out.println(tables.size());
        List<Notice> noticeList = new ArrayList<>();
        for (int i = 0; i < tables.size() - 1; i++) {
            Notice notice = new Notice();
            Element table = tables.get(i);
            org.jsoup.select.Elements spans = table.getElementsByTag("span");
            String url = spans.first().select("a").attr("href");
            String title = spans.first().text();
            String uptime = spans.get(1).text();
            notice.setTitle(title);
            notice.setUrl("http://jwc.tyut.edu.cn/" + url);
            notice.setUpdate(uptime);
            noticeList.add(notice);
        }
    }
    @Test //notice info
    public void noticeinfo() throws IOException {
        GetCookieUtil getCookieUtil = new GetCookieUtil();
        String cookie = getCookieUtil.getCookieFromNotice();
        SendGet sendGet = new SendGet();
        String elements = String.valueOf(sendGet.getnoticeInfo("1850"));
        String result = elements.replace("/uploads","http://jwc.tyut.edu.cn/uploads");
        System.out.println(result);
    }
    @Test //help list1固定岗  a标签需要改动
    public void helplist1() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet("http://qgzx.tyut.edu.cn/sec_zpzx.jsp?dt=nnpmEefYrudX/AfSe+aApv7gIcFy0len&jx=B8cmjbKnnOZkXcZog7FtLhNJ+LQ+gy78");
        HttpGet httpGet = new HttpGet("http://qgzx.tyut.edu.cn/sec_zpzx.jsp?dt=nnpmEefYrudX/AfSe+aApv7gIcFy0len&jx=nnpmEefYrudX/AfSe+aApv7gIcFy0len");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        String result = EntityUtils.toString(httpResponse.getEntity(),"utf-8");
        Document doc = Jsoup.parse(result);
        org.jsoup.select.Elements element = doc.getElementsByTag("table");
        Element listTables = element.get(8);
        /*String result2 = listTables.replace("<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" border=\"0\" width=\"1000\" height=\"89\"> \n" +
                " <tbody>\n" +
                "  <tr valign=\"top\"> \n" +
                "   <td height=\"35\" colspan=\"5\"></td> \n" +
                "  </tr> \n" +
                "  <tr valign=\"top\"> \n" +
                "   <td width=\"12\"></td> \n" +
                "   <td width=\"178\" align=\"center\"> \n" +
                "    <table cellspacing=\"0\" cellpadding=\"0\" width=\"178\" align=\"center\"> \n" +
                "     <tbody>\n" +
                "      <tr valign=\"top\"> \n" +
                "       <td align=\"left\" style=\"background-image:url('images/search.jpg'); background-repeat:no-repeat;\" width=\"178\" height=\"30\"> \n" +
                "        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"left\"> \n" +
                "         <tbody>\n" +
                "          <tr> \n" +
                "           <td height=\"5\" colspan=\"3\"></td> \n" +
                "          </tr> \n" +
                "          <tr> \n" +
                "           <td width=\"5\"> </td> \n" +
                "           <td> <input id=\"myedit\" name=\"keywords\" type=\"text\" style=\"width: 120px; height: 22px;\" value=\"请输入查询关键字...\" onmouseover=\"this.focus()\" onmouseout=\"if(this.value=='')this.value='请输入查询关键字...';\" onfocus=\"this.select()\" onclick=\"if(this.value=='请输入查询关键字...')this.value=''\" /> </td> \n" +
                "           <td> </td> \n" +
                "          </tr> \n" +
                "         </tbody>\n" +
                "        </table> </td> \n" +
                "      </tr> \n" +
                "      <tr valign=\"top\"> \n" +
                "       <td height=\"15\"> </td> \n" +
                "      </tr> \n" +
                "      <tr valign=\"top\"> \n" +
                "       <td align=\"center\" width=\"178\" height=\"340\" style=\"background-image:url('images/second_cd_zpzx.jpg'); background-repeat:no-repeat;\"> \n" +
                "        <table cellspacing=\"0\" cellpadding=\"0\" width=\"178\" align=\"center\" class=\"secer_leftdha\"> \n" +
                "         <tbody>\n" +
                "          <tr valign=\"top\"> \n" +
                "           <td align=\"center\" height=\"20\" colspan=\"3\"> </td> \n" +
                "          </tr> \n" +
                "          <tr valign=\"middle\"> \n" +
                "           <td width=\"12\"></td> \n" +
                "           <td align=\"center\" width=\"153\" height=\"34\" style=\"background-image:url('images/second_leftbar.jpg'); background-repeat:no-repeat;\"> <a href=\"sec_zpzx.jsp?dt=nnpmEefYrudX/AfSe+aApv7gIcFy0len&amp;jx=B8cmjbKnnOZkXcZog7FtLhNJ+LQ+gy78\">固定岗</a> </td> \n" +
                "           <td width=\"12\"></td> \n" +
                "          </tr> \n" +
                "          <tr valign=\"top\"> \n" +
                "           <td align=\"center\" height=\"5\" colspan=\"3\"> </td> \n" +
                "          </tr> \n" +
                "          <tr valign=\"middle\"> \n" +
                "           <td width=\"12\"></td> \n" +
                "           <td align=\"center\" width=\"153\" height=\"34\" style=\"background-image:url('images/second_leftbar.jpg'); background-repeat:no-repeat;\"> <a href=\"sec_zpzx.jsp?dt=nnpmEefYrudX/AfSe+aApv7gIcFy0len&amp;jx=nnpmEefYrudX/AfSe+aApv7gIcFy0len\">临时岗</a> </td> \n" +
                "           <td width=\"12\"></td> \n" +
                "          </tr> \n" +
                "          <tr valign=\"top\"> \n" +
                "           <td align=\"center\" height=\"5\" colspan=\"3\"> </td> \n" +
                "          </tr> \n" +
                "          <tr valign=\"middle\"> \n" +
                "           <td width=\"12\"></td> \n" +
                "           <td align=\"center\" width=\"153\" height=\"34\" style=\"background-image:url('images/second_leftbar.jpg'); background-repeat:no-repeat;\"> <a href=\"sec_zpzx.jsp?dt=nnpmEefYrudX/AfSe+aApv7gIcFy0len&amp;jx=9mh5186NSY7s5s8lrFQS7S4oJZ0WUZPq\">专业技术岗</a> </td> \n" +
                "           <td width=\"12\"></td> \n" +
                "          </tr> \n" +
                "          <tr valign=\"top\"> \n" +
                "           <td align=\"center\" height=\"5\" colspan=\"3\"> </td> \n" +
                "          </tr> \n" +
                "          <tr valign=\"middle\"> \n" +
                "           <td width=\"12\"></td> \n" +
                "           <td align=\"center\" width=\"153\" height=\"34\" style=\"background-image:url('images/second_leftbar.jpg'); background-repeat:no-repeat;\"> <a href=\"sec_zpzx.jsp?dt=9mh5186NSY7s5s8lrFQS7S4oJZ0WUZPq&amp;jx=nnpmEefYrudX/AfSe+aApv7gIcFy0len\">校外岗</a> </td> \n" +
                "           <td width=\"12\"></td> \n" +
                "          </tr> \n" +
                "          <tr valign=\"top\"> \n" +
                "           <td align=\"center\" height=\"5\" colspan=\"3\"> </td> \n" +
                "          </tr> \n" +
                "          <tr valign=\"middle\"> \n" +
                "           <td width=\"12\"></td> \n" +
                "           <td align=\"center\" width=\"153\" height=\"34\" style=\"background-image:url('images/second_leftbar.jpg'); background-repeat:no-repeat;\"> <a href=\"sec_rc.jsp\"><span style=\"color: #00FF00\">人才市场</span></a> </td> \n" +
                "           <td width=\"12\"></td> \n" +
                "          </tr> \n" +
                "          <tr valign=\"top\"> \n" +
                "           <td align=\"center\" height=\"5\" colspan=\"3\"> </td> \n" +
                "          </tr> \n" +
                "          <tr valign=\"middle\"> \n" +
                "           <td width=\"12\"></td> \n" +
                "           <td align=\"center\" width=\"153\" height=\"34\" style=\"background-image:url('images/second_leftbar.jpg'); background-repeat:no-repeat;\"> <a href=\"sec_zpgg.jsp?navid=nnpmEefYrudX/AfSe+aApv7gIcFy0len\"><span style=\"color: #00FF00\">面试通知</span></a> </td> \n" +
                "           <td width=\"12\"></td> \n" +
                "          </tr> \n" +
                "          <tr valign=\"top\"> \n" +
                "           <td align=\"center\" height=\"5\" colspan=\"3\"> </td> \n" +
                "          </tr> \n" +
                "          <tr valign=\"middle\"> \n" +
                "           <td width=\"12\"></td> \n" +
                "           <td align=\"center\" width=\"153\" height=\"34\" style=\"background-image:url('images/second_leftbar.jpg'); background-repeat:no-repeat;\"> <a href=\"sec_zpgg.jsp?navid=B8cmjbKnnOZkXcZog7FtLhNJ+LQ+gy78\"><span style=\"color: #00FF00\">录用公告</span></a> </td> \n" +
                "           <td width=\"12\"></td> \n" +
                "          </tr> \n" +
                "         </tbody>\n" +
                "        </table> </td> \n" +
                "      </tr> \n" +
                "      <tr valign=\"top\"> \n" +
                "       <td height=\"15\"> </td> \n" +
                "      </tr> \n" +
                "      <tr valign=\"top\"> \n" +
                "       <td width=\"178\" height=\"55\" style=\"background-image:url('images/second_conus.jpg'); background-repeat:no-repeat;\"> \n" +
                "        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"left\" class=\"secer_leftdha\"> \n" +
                "         <tbody>\n" +
                "          <tr> \n" +
                "           <td height=\"18\" colspan=\"2\"></td> \n" +
                "          </tr> \n" +
                "          <tr> \n" +
                "           <td width=\"75\"> </td> \n" +
                "           <td align=\"center\" class=\"secer_leftdh\"> <a href=\"mailto:qgzx@tyut.edu.cn\">联系我们&nbsp;-&gt;</a> </td> \n" +
                "          </tr> \n" +
                "         </tbody>\n" +
                "        </table> </td> \n" +
                "      </tr> \n" +
                "     </tbody>\n" +
                "    </table> </td> \n" +
                "   <td align=\"center\" width=\"20\" height=\"505\" style=\"background-image:url('images/second_gcbg.jpg'); background-repeat:no-repeat;\"> </td> ","");
        String result3 = result2.replace(" <tr valign=\"middle\"> \n" +
                "       <td width=\"778\" height=\"52\" style=\"background-image:url('images/second_dhbar.jpg'); background-repeat:no-repeat;\"> \n" +
                "        <table cellspacing=\"0\" cellpadding=\"0\" width=\"778\" align=\"center\"> \n" +
                "         <tbody>\n" +
                "          <tr valign=\"top\"> \n" +
                "           <td width=\"70\"></td> \n" +
                "           <td class=\"sectitle\">招聘中心</td> \n" +
                "           <td class=\"secdhwz\" width=\"600\" align=\"right\">你现在的位置：首页&nbsp;&gt;&nbsp;招聘中心&nbsp;&gt;&nbsp; 校内岗位&nbsp;&gt;&nbsp;固定岗 </td> \n" +
                "          </tr> \n" +
                "         </tbody>\n" +
                "        </table> </td> \n" +
                "      </tr> \n" +
                "      <tr valign=\"top\"> \n" +
                "       <td height=\"15\" width=\"778\" align=\"center\"> \n" +
                "        <form name=\"ggform\" action=\"/fq?cmd=sec_zpzx\" method=\"post\" style=\"padding: 0px;margin: 0px;\"> \n" +
                "         <input id=\"dt\" name=\"dt\" value=\"nnpmEefYrudX/AfSe+aApv7gIcFy0len\" type=\"hidden\" /> \n" +
                "         <input id=\"jx\" name=\"jx\" value=\"B8cmjbKnnOZkXcZog7FtLhNJ+LQ+gy78\" type=\"hidden\" /> \n" +
                "         <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" align=\"center\"> \n" +
                "          <tbody>\n" +
                "           <tr valign=\"middle\"> \n" +
                "            <td height=\"25\"> <select name=\"search_zpzx_dept\" style=\"width: 240px; height: 20px; border: 1px solid rgb(0, 102, 204);\"> <option value=\"\" selected=\"selected\">部门</option> <option value=\"1\">现代科技学院</option> <option value=\"2\">机械工程学院</option> <option value=\"3\">材料学院</option> <option value=\"4\">电力学院</option> <option value=\"5\">信息学院</option> <option value=\"6\">计算机科学与技术学院</option> <option value=\"8\">建工学院</option> <option value=\"9\">环工学院</option> <option value=\"10\">水利学院</option> <option value=\"11\">化工学院</option> <option value=\"12\">矿业学院</option> <option value=\"13\">经管学院</option> <option value=\"14\">政法学院</option> <option value=\"15\">外国语学院</option> <option value=\"16\">体育学院</option> <option value=\"17\">数学学院</option> <option value=\"18\">轻纺学院</option> <option value=\"19\">测试学院</option> <option value=\"20\">物理学院</option> <option value=\"21\">力学学院</option> <option value=\"22\">国际交流学院</option> <option value=\"23\">艺术学院</option> <option value=\"24\">马克思主义学院</option> <option value=\"25\">软件学院</option> <option value=\"26\">大数据学院</option> <option value=\"101\">校长办公室</option> <option value=\"102\">人事处</option> <option value=\"103\">教务处</option> <option value=\"104\">科技处</option> <option value=\"105\">勤工助学中心</option> <option value=\"106\">学生处教育科</option> <option value=\"107\">学生处资助科</option> <option value=\"108\">学生处档案室</option> <option value=\"109\">清泽心雨</option> <option value=\"110\">心理健康与咨询中心</option> <option value=\"111\">国有资产管理处</option> <option value=\"112\">后勤保障处</option> <option value=\"113\">保卫处</option> <option value=\"114\">离退休管理处</option> <option value=\"115\">发展规划处</option> <option value=\"116\">招生与就业处</option> <option value=\"117\">党委办公室</option> <option value=\"118\">机关党委</option> <option value=\"119\">党校</option> <option value=\"120\">组织部</option> <option value=\"121\">宣传部</option> <option value=\"122\">校报</option> <option value=\"123\">广播电台</option> <option value=\"124\">工会</option> <option value=\"125\">校团委</option> <option value=\"126\">团委礼仪队</option> <option value=\"127\">国际合作处</option> <option value=\"128\">测控技术研究所</option> <option value=\"129\">图书馆</option> <option value=\"130\">档案馆</option> <option value=\"131\">工程训练中心</option> <option value=\"132\">水电暖管理服务中心</option> <option value=\"133\">交安委</option> <option value=\"134\">邮电通讯服务中心</option> <option value=\"135\">校友联谊会</option> <option value=\"136\">公寓管理服务中心</option> <option value=\"137\">直属党委</option> <option value=\"138\">校园规划与建设处</option> <option value=\"139\">居委会</option> <option value=\"140\">精细化工研究所</option> <option value=\"141\">继续教育学院</option> <option value=\"142\">纪检委</option> <option value=\"143\">信息化管理与建设中心</option> <option value=\"144\">物业管理服务中心</option> <option value=\"145\">饮食中心</option> <option value=\"146\">国资处场馆</option> <option value=\"147\">测试用工部门</option> <option value=\"148\">物业中心校区</option> <option value=\"149\">校庆办</option> <option value=\"150\">现代科技学院后勤服务中心</option> <option value=\"151\">研究生院</option> <option value=\"152\">维修中心</option> <option value=\"154\">人文中心</option> <option value=\"155\">思政科国防生</option> <option value=\"156\">发展科</option> <option value=\"157\">东社区</option> <option value=\"158\">西社区</option> <option value=\"159\">电视台</option> <option value=\"160\">学生处思政科办公室</option> <option value=\"161\">信息中心多媒体</option> <option value=\"162\">语音教室</option> <option value=\"163\">期刊中心</option> <option value=\"165\">校医院</option> <option value=\"166\">计财处</option> <option value=\"167\">力学实验室</option> <option value=\"168\">产业管理办公室</option> <option value=\"169\">资产公司党委</option> <option value=\"170\">后勤党委</option> <option value=\"171\">子弟小学</option> <option value=\"172\">教材供应中心</option> <option value=\"173\">煤化所</option> <option value=\"174\">审计处</option> <option value=\"175\">直属团工委</option> <option value=\"176\">统战部</option> <option value=\"177\">武装部</option> <option value=\"178\">明向管委会综合部</option> <option value=\"179\">明向校区学生工作部</option> <option value=\"181\">机械学院教科办（助教）</option> <option value=\"182\">数学学院教科办（助教）</option> <option value=\"183\">信息学院教科办（助教）</option> <option value=\"184\">力学学院教科办（助教）</option> <option value=\"185\">经管学院教科办（助教）</option> <option value=\"186\">电力学院教科办（助教）</option> <option value=\"187\">化工学院教科办（助教）</option> <option value=\"188\">物电学院教科办（助教）</option> <option value=\"189\">材料学院教科办（助教）</option> <option value=\"190\">艺术学院教科办（助教）</option> <option value=\"191\">新材料工程技术研究中心</option> <option value=\"192\">表面所</option> <option value=\"193\">应力所</option> <option value=\"194\">机电所</option> <option value=\"195\">采煤所</option> <option value=\"196\">矿业学院教科办（助教）</option> <option value=\"197\">轻纺学院教科办（助教）</option> <option value=\"198\">建工学院教科办（助教）</option> <option value=\"199\">计软学院教科办（助教）</option> <option value=\"200\">体育学院教科办（助教）</option> <option value=\"201\">环工学院教科办</option> <option value=\"1000\">华融资产管理公司</option> <option value=\"1001\">晋能清洁能源光伏工程有限责任公司</option> <option value=\"1002\">中国铁塔山西省分公司</option> <option value=\"1003\">铁塔晋中分公司</option> <option value=\"1004\">尚易友</option> </select> </td> \n" +
                "            <td height=\"25\"> <select name=\"search_zpzx_stuclass\" style=\"width: 80px; height: 20px; border: 1px solid rgb(0, 102, 204);\"> <option value=\"\" selected=\"selected\">学历限制</option> <option value=\"本科生\">本科生</option> <option value=\"研究生\">研究生</option> <option value=\"不限制\">不限制</option> </select> </td> \n" +
                "            <td align=\"left\"> <input name=\"search_zpzx_jobname\" id=\"search_zpzx_jobname\" style=\"width: 300px; height: 16px; border: 1px solid rgb(0, 102, 204);\" type=\"text\" placeholder=\"请输入岗位名称\" /> </td> \n" +
                "            <td> <input name=\"B1\" style=\"width: 50px; height: 20px; border: 1px solid rgb(0, 102, 204);\" value=\"查询\" type=\"submit\" /> </td> \n" +
                "           </tr> \n" +
                "          </tbody>\n" +
                "         </table> \n" +
                "        </form> </td> \n" +
                "      </tr> ","");
        String result4 = result3.replace("<tr valign=\"middle\"> \n" +
                "           <td width=\"778\" height=\"40\"> \n" +
                "            <table cellspacing=\"0\" cellpadding=\"0\" width=\"778\" align=\"center\"> \n" +
                "             <tbody>\n" +
                "              <tr valign=\"middle\"> \n" +
                "               <td align=\"center\" width=\"100%\" class=\"seccondetail\"> \n" +
                "                <form method=\"post\" name=\"fPageNum\" action=\"sec_zpzx.jsp?dt=nnpmEefYrudX/AfSe+aApv7gIcFy0len&amp;jx=B8cmjbKnnOZkXcZog7FtLhNJ+LQ+gy78\" style=\"padding: 0px;margin: 0px;\">\n" +
                "                 <p align=\"left\">&gt;&gt;分页&nbsp;<font color=\"999966\"> 首页 上一页</font>&nbsp;<a href=\"sec_zpzx.jsp?dt=nnpmEefYrudX/AfSe+aApv7gIcFy0len&amp;jx=B8cmjbKnnOZkXcZog7FtLhNJ+LQ+gy78&amp;page=2 \">下一页</a>&nbsp;<a href=\"sec_zpzx.jsp?dt=nnpmEefYrudX/AfSe+aApv7gIcFy0len&amp;jx=B8cmjbKnnOZkXcZog7FtLhNJ+LQ+gy78&amp;page=224\">尾页</a>&nbsp;&nbsp;页次：<strong><font color=\"red\">1</font>/224</strong>页&nbsp;共<b>4469</b>条记录 <b>20</b>条/页&nbsp;转到第<input type=\"text\" name=\"page\" style=\"width:30px; height:15px; text-align:center; border: 1px solid rgb(0, 102, 204);\" maxlength=\"10\" class=\"smallInput\" value=\"1\" />页 <input class=\"buttonface\" type=\"submit\" value=\"Go\" name=\"cndok\" style=\"width:35px; height:18px; cursor:hand; border: 1px solid rgb(0, 102, 204);\" /></p>\n" +
                "                </form> </td> \n" +
                "              </tr> \n" +
                "             </tbody>\n" +
                "            </table> </td> \n" +
                "          </tr> ","");
        System.out.println(result4);
*/
    }
    @Test  //list2  临时岗
    public void list2() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://qgzx.tyut.edu.cn/sec_zpzx.jsp?dt=nnpmEefYrudX/AfSe+aApv7gIcFy0len&jx=B8cmjbKnnOZkXcZog7FtLhNJ+LQ+gy78");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        String result = EntityUtils.toString(httpResponse.getEntity(),"utf-8");
        Document doc = Jsoup.parse(result);
        org.jsoup.select.Elements element = doc.getElementsByTag("td");
//        Element listTables = element.get(8);
        for (int i=108;i<element.size()-1;i++){
            String tds = element.get(i).text();
            i=i+2;
            Pattern p = Pattern.compile("^[\\u4e00-\\u9fa5]+");   //type
            Matcher matcher = p.matcher(tds);
            if (matcher.find()){
                System.out.println(matcher.group());
            }
            Pattern p1 = Pattern.compile("\\([\\u4e00-\\u9fa5]*\\)[\\u4e00-\\u9fa5]*\\s?\\s?\\s?\\([\\u4e00-\\u9fa5]*\\)\\s?\\s?\\s?\\([\\u4e00-\\u9fa5]*\\)");   //title
            Matcher matcher1 = p1.matcher(tds);
            if (matcher1.find()){
                System.out.println(matcher1.group());
            }
            Pattern p2 = Pattern.compile("\\d{4}\\-\\d{2}\\-\\d{2}\\s\\d{2}\\:\\d{2}");     //日期
            Matcher matcher2 = p2.matcher(tds);
            if (matcher2.find()){
                System.out.println(matcher2.group());
            }
            Pattern p3 = Pattern.compile("\\s[\\u4e00-\\u9fa5]{1,3}\\s");   //不限制
            Matcher matcher3 = p3.matcher(tds);
            if (matcher3.find()){
                System.out.println(matcher3.group());
            }
            Pattern p4 = Pattern.compile("\\s[\\u4e00-\\u9fa5]{4,5}");   //报名已经截至
            Matcher matcher4 = p4.matcher(tds);
            if (matcher4.find()){
                System.out.println(matcher4.group());
            }
            Pattern p5 = Pattern.compile("\\s\\d*\\/\\d*");   //   1/1
            Matcher matcher5 = p5.matcher(tds);
            if (matcher5.find()){
                System.out.println(matcher5.group());
            }
//            System.out.println(tds);

        }
    }
    @Test
    public void regix(){
//        String str = "2015/07/20 11:01 来源: 测试";
//        String regex = "\\d{4}[-|/]\\d{2}[-|/]\\d{2} \\d{2}:\\d{2}";
//        Pattern p = Pattern.compile(regex);
//        Matcher matcher = p.matcher(str);
//        if (matcher.find()) {
//            System.out.println(matcher.group());
//        }
        String url = "th_gw.jsp?dt=nnpmEefYrudX/AfSe+aApv7gIcFy0len&jx=nnpmEefYrudX/AfSe+aApv7gIcFy0len&jobid=+ntM2mKqvugVuGqLHK5C9+Bn7qHJ0OEwmhlLKvhEvPFS4BkCEYQkq/XDJFhcyw6EpmwZST+fLVg=";
        String url2 = url.split("\\?")[1];
        System.out.println(url2);
    }
    @Test
    public void helpInfo() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://qgzx.tyut.edu.cn/th_gw.jsp?dt=nnpmEefYrudX/AfSe+aApv7gIcFy0len&jx=nnpmEefYrudX/AfSe+aApv7gIcFy0len&jobid=7wdCsAC9utkaJRXcHHKLF7f96YCnhli0Tf3tT7F3MeeWlH7yQe0q4SxrfuaTDWLd6aRReh5dYvQ=");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
        Document doc = Jsoup.parse(result);
        org.jsoup.select.Elements element = doc.getElementsByTag("tbody");
        String tbody = String.valueOf(element.get(15));
        String resultInfo = tbody.replace("images/xz.png","http://qgzx.tyut.edu.cn/images/xz.png");
        System.out.println(resultInfo);
    }
    //登录
    @Test
    public void gradeCrawl() throws IOException {
        String cookiePre = String.valueOf(Jsoup.connect("http://202.207.247.44:8089").execute().cookies());
        String cookie = cookiePre.replace("{", "").replace("}", "");
        SendPost sendPost = new SendPost();
        String checkCode = sendPost.getCheckCode_1(cookie);
        System.out.println(checkCode);
        String result = sendPost.sendPost("2016006569","283116", checkCode, cookie);
        if (result.contains("你输入的验证码错误，请您重新输入！")) {
            for (int i = 0; i < 20; i++) {
                String checkCode2Pre = sendPost.getCheckCode_1(cookie);
                String checkCode2 = checkCode2Pre.substring(0, 4);
                String result2 = sendPost.sendPost("2016006569", "283116", checkCode2, cookie);
                if (result2.contains("你输入的验证码错误，请您重新输入！"))
                    continue;
                else if (result2.contains("您的密码不正确，请您重新输入！")){
                    System.out.println("您的密码不正确，请您重新输入");
//                    mv.addObject("message","请您输入正确的帐号密码");
//                    mv.setViewName("login");
//                    return mv;
                }else
                    System.out.println(result2 + "登录成功：");
                break;
            }
        }
        System.out.println(cookie);
    }
    //查询成绩
    @Test
    public void grade() throws IOException {
        SendGet sendGet = new SendGet();
        String result = sendGet.sendGetCodeTable("JSESSIONID=afb09OrTErixC66Drvz4v");
        Document doc = Jsoup.parse(result);
        Elements trs = doc.select(".odd");
        System.out.println(trs.size());
        for (Element tr:trs){
            Elements tds = tr.getElementsByTag("td");
            String subject = tds.get(2).text();
            String credit = tds.get(4).text();
            String property = tds.get(5).text();
            String score = tds.get(6).text();
            System.out.println(subject+"\n"+credit+"\n"+property+"\n"+score);
        }

//        for (int i=1;i<trs.size();i++){
//            Element tr = trs.get(i);
//            Elements tds = tr.getElementsByTag("td");
//            String subject = tds.get(2).text();
//            String credit = tds.get(4).text();
//            String property = tds.get(5).text();
//            String score = tds.get(6).text();
//            System.out.println(subject+"\n"+credit+"\n"+property+"\n"+score);
////            System.out.println(tds);
//        }
//        System.out.println(trs);

    }
    //查询排名
    @Test
    public void rank() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String cookiePre = String.valueOf(Jsoup.connect("http://202.207.247.60/").execute().cookies());
        String cookie = cookiePre.replace("{", "").replace("}", "");
        System.out.println(cookie);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://202.207.247.60/Pages/Cj/Xyyj/Xsgrcj.aspx");

        System.out.println("请输入验证码");
        //String checkCode = scanner.next();
        List<BasicNameValuePair> formParams = new ArrayList<>();
        httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpPost.setHeader("Accept-Encoding", "gzip, deflate");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("Cookie", cookie+";ValidateCode=5ncuf;512610BFAA3692D1=D9C50517D660B308; 1B8CF2BA879BBCEFD588FFEF45F4514F=3BE5071BB5291BEDC808F9B793993091; FE71C29C107FD41840E03E3C77C1E504=4D529692BAFABDF7; B56CAB057E98CEA01AA21270A577C9D3=8157603D753149D6; 2587657B10AEBB0B=; D839906CC55542B9=84C1CBE661C00CDE; F5CC61A7AF3AE4FF=E895594D8744FCB1; 9BACB9BEC5A5CEC9=73E475E459DB246A");
//        httpPost.setHeader("Host", "202.207.247.49");//202.207.247.44:8089
        httpPost.setHeader("Host", "202.207.247.60");
        httpPost.setHeader("Upgrade-Insecure-Requests", "1");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams, "gb2312");
        httpPost.setEntity(formEntity);
        //System.out.println("executing request: " + httpPost.getURI());
        //执行post请求
        CloseableHttpResponse response = httpclient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String resultPre = EntityUtils.toString(entity, "gb2312");
        Document doc = Jsoup.parse(resultPre);
        String doc2 = String.valueOf(doc.clone());
        String result = doc2.replace("/Content/bootstrap3.3.4/bootstrap.min.css","http://202.207.247.60/Content/bootstrap3.3.4/bootstrap.min.css")
                .replace("/Content/BootstrapTable/bootstrap-table.css","http://202.207.247.60/Content/BootstrapTable/bootstrap-table.css")
                .replace("/Content/Style.css","http://202.207.247.60/Content/Style.css")
                .replace("/Scripts/jquery-1.10.2.min.js","http://202.207.247.60/Scripts/jquery-1.10.2.min.js")
                .replace("/Content/BootstrapTable/bootstrap-table.js","http://202.207.247.60/Content/BootstrapTable/bootstrap-table.js")
                .replace("/Scripts/bootstrap3.3.4/bootstrap.min.js","http://202.207.247.60/Scripts/bootstrap3.3.4/bootstrap.min.js")
                .replace("/Content/BootstrapTable/bootstrap-table.js","/Content/BootstrapTable/bootstrap-table.js")
                .replace("/Content/BootstrapTable/locale/bootstrap-table-zh-CN.js","http://202.207.247.60/Content/BootstrapTable/locale/bootstrap-table-zh-CN.js")
                .replace("/Scripts/Typeahead/jquery.typeahead.min.css","http://202.207.247.60/Scripts/Typeahead/jquery.typeahead.min.css")
                .replace("/Scripts/Typeahead/jquery.typeahead.min.js","http://202.207.247.60/Scripts/Typeahead/jquery.typeahead.min.js")
                .replace("/Scripts/Untils.js","http://202.207.247.60/Scripts/Untils.js")
                .replace("/Scripts/PoshyTip/tip-yellowsimple/tip-yellowsimple.css","http://202.207.247.60/Scripts/PoshyTip/tip-yellowsimple/tip-yellowsimple.css")
                .replace("/Scripts/PoshyTip/jquery.poshytip.min.js","http://202.207.247.60/Scripts/PoshyTip/jquery.poshytip.min.js")
                .replace("/LoginN.aspx","http://202.207.247.60/LoginN.aspx")
                .replace("/Hander/Cj/CjAjax.ashx?rnd","http://202.207.247.60/Hander/Cj/CjAjax.ashx?rnd");
        Element element = doc.body();
        Elements trs = doc.select(".table table-hover table-striped");

        System.out.println("element++++++++++++++++++++++++++++++++++++++"+element);
        System.out.println("result++++++++++++++++++++++++++++++++++++++++"+result);
        System.out.println("trs++++++++++++++++++++++++++++++++++++++++++"+trs);

    }

    @Test
    public void classTable() throws IOException {
        SendGet sendGet = new SendGet();
        String resultClass = sendGet.sendGetClassTable("JSESSIONID=agfeIxu8-6o__4_cuS84v");
        Document doc = Jsoup.parse(resultClass);
        Elements trs = doc.select(".pageAlign tr");
        List<ClassTable> list = new ArrayList<>();
        System.out.println("到这里了");
        for (int i=1;i<2;i++){
            ClassTable table = new ClassTable();
            Elements tds = trs.get(i).getElementsByTag("td");
            table.setTime(tds.get(1).text());
            table.setMonday(tds.get(2).text());
            table.setTuesday(tds.get(3).text());
            table.setWednesday(tds.get(4).text());
            table.setThursday(tds.get(5).text());
            table.setFriday(tds.get(6).text());
            table.setSaturday(tds.get(7).text());
            table.setSunday(tds.get(8).text());
            list.add(table);
        }
        for (ClassTable list2:list){
            System.out.println(list2.toString());
        }
        System.out.println("成功获取第一列");

        for (int i=2;i<5;i++){
            ClassTable table = new ClassTable();
            Elements tds = trs.get(i).getElementsByTag("td");
            table.setTime(tds.get(0).text());
            table.setMonday(tds.get(1).text());
            table.setTuesday(tds.get(2).text());
            table.setWednesday(tds.get(3).text());
            table.setThursday(tds.get(4).text());
            table.setFriday(tds.get(5).text());
            table.setSaturday(tds.get(6).text());
            table.setSunday(tds.get(7).text());
            list.add(table);
        }
        for (ClassTable list2:list){
            System.out.println(list2.toString());
        }
        System.out.println("成功获取第er列");

        for (int i=6;i<7;i++){
            ClassTable table = new ClassTable();
            Elements tds = trs.get(i).getElementsByTag("td");
            table.setTime(tds.get(1).text());
            table.setMonday(tds.get(2).text());
            table.setTuesday(tds.get(3).text());
            table.setWednesday(tds.get(4).text());
            table.setThursday(tds.get(5).text());
            table.setFriday(tds.get(6).text());
            table.setSaturday(tds.get(7).text());
            table.setSunday(tds.get(8).text());
            list.add(table);
        }
        for (ClassTable list2:list){
            System.out.println(list2.toString());
        }
        System.out.println("成功获取第3列");

        for (int i=7;i<10;i++){
            ClassTable table = new ClassTable();
            Elements tds = trs.get(i).getElementsByTag("td");
            table.setTime(tds.get(0).text());
            table.setMonday(tds.get(1).text());
            table.setTuesday(tds.get(2).text());
            table.setWednesday(tds.get(3).text());
            table.setThursday(tds.get(4).text());
            table.setFriday(tds.get(5).text());
            table.setSaturday(tds.get(6).text());
            table.setSunday(tds.get(7).text());
            list.add(table);
        }
        for (ClassTable list2:list){
            System.out.println(list2.toString());
        }
        for (int i=11;i<12;i++){
            ClassTable table = new ClassTable();
            Elements tds = trs.get(i).getElementsByTag("td");
            table.setTime(tds.get(1).text());
            table.setMonday(tds.get(2).text());
            table.setTuesday(tds.get(3).text());
            table.setWednesday(tds.get(4).text());
            table.setThursday(tds.get(5).text());
            table.setFriday(tds.get(6).text());
            table.setSaturday(tds.get(7).text());
            table.setSunday(tds.get(8).text());
            list.add(table);
        }
        for (ClassTable list2:list){
            System.out.println(list2.toString());
        }
        for (int i=12;i<14;i++){
            ClassTable table = new ClassTable();
            Elements tds = trs.get(i).getElementsByTag("td");
            table.setTime(tds.get(0).text());
            table.setMonday(tds.get(1).text());
            table.setTuesday(tds.get(2).text());
            table.setWednesday(tds.get(3).text());
            table.setThursday(tds.get(4).text());
            table.setFriday(tds.get(5).text());
            table.setSaturday(tds.get(6).text());
            table.setSunday(tds.get(7).text());
            list.add(table);
        }
        for (ClassTable list2:list){
            System.out.println(list2.toString());
        }
    }

    @Test
    public void ClassArrange() throws IOException {
        SendGet sendGet = new SendGet();
        String resultClass = sendGet.sendGetClassTable("JSESSIONID=aedkHBCTi1n2Uh9TkP94v");
        Document doc = Jsoup.parse(resultClass);
        Elements trs =doc.select(".odd");
//        for (int i =15;i<trs.size();i++ ){
//            System.out.println(trs.get(16).getElementsByTag("td").get(1));
//            trs.get()   0  1  3  4   5  6    8 9  10
//        }
        for (Element tr:trs){
            Elements tds = tr.getElementsByTag("td");
            if (tds.size()>7){
                tds.remove(0);
                tds.get(3).remove();
                tds.remove();
                tds.remove(4).empty();
                tds.remove(5).remove();
                tds.remove(6).remove();
                tds.remove(8).remove();
                tds.remove(9).remove();
//                tds.remove(10);
                System.out.println(tds);
            }else {
                System.out.println("暂时不处理");
            }
        }


        System.out.println("trs++++++++++++++++++++++++++++++++++"+trs);
    }

}

