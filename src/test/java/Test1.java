import com.swjtu.lang.Lang;
import com.swjtu.querier.Querier;
import com.swjtu.trans.*;
import org.junit.Test;

import java.util.List;

public class Test1 {
    @Test
    public void test1() {
        Querier querier = Querier.getQuerier();                                  // 获取查询器

        querier.setParams(Lang.EN, Lang.CHT, "what");    // 设置参数

        querier.attach(new Google());                                            // 向查询器中添加 Google 翻译器
        querier.attach(new Youdao());                                            // 向查询器中添加 Youdao 翻译器
        querier.attach(new Baidu());                                             // 向查询器中添加 Baidu 翻译器
        querier.attach(new Tencent());                                           // 向查询器中添加 Tencent 翻译器
        querier.attach(new Omi());                                               // 向查询器中添加 Omi 翻译器
        querier.attach(new Trycan());                                            // 向查询器中添加 Trycan 翻译器
        querier.attach(new Iciba());                                             // 向查询器中添加 Iciba 翻译器
        querier.attach(new Sogou());                                             // 向查询器中添加 Sogou 翻译器

        List<String> result = querier.execute();                                 // 执行查询并接收查询结果

        for (String str : result) {
            System.out.println(str);
        }
    }

}
