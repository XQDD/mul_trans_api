package com.xqdd;

import com.swjtu.lang.Lang;
import com.swjtu.querier.Querier;
import com.swjtu.trans.Google;
import com.swjtu.trans.Iciba;
import com.swjtu.trans.Sogou;
import com.swjtu.trans.Youdao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = "application/json; charset=utf-8")
public class Controller {

    private Querier querier = Querier.getQuerier();                                  // 获取查询器

    public Controller() {
        querier.attach(new Youdao());                                            // 向查询器中添加 Youdao 翻译器
        querier.attach(new Google());                                            // 向查询器中添加 Google 翻译器
//        querier.attach(new Baidu());                                             // 向查询器中添加 Baidu 翻译器
//        querier.attach(new Tencent());                                           // 向查询器中添加 Tencent 翻译器
//        querier.attach(new Omi());                                               // 向查询器中添加 Omi 翻译器
//        querier.attach(new Trycan());                                            // 向查询器中添加 Trycan 翻译器
        querier.attach(new Iciba());                                             // 向查询器中添加 Iciba 翻译器
        querier.attach(new Sogou());                                             // 向查询器中添加 Sogou 翻译器

    }

    @PostMapping("translate")
    public Msg translate(@RequestBody(required = false) TransRequestInfo requestInfo) {
        if (requestInfo == null || StringUtils.isBlank(requestInfo.word)) {
            return new Msg(0, "invalid arguments");
        }
        Lang originLang = getLang(requestInfo.originLang);
        Lang targetLang = getLang(requestInfo.targetLang);
        if (originLang == null) {
            originLang = Lang.AUTO;
        }
        if (targetLang == null) {
            return new Msg(0, "target language is not support or invalid");
        }
        querier.setParams(originLang, targetLang, requestInfo.word);    // 设置参数
        List<String> result = querier.execute();
        if (result == null) {
            return new Msg(0);
        }
        List<String> list = result.stream().map(str -> {
            str = str.trim();
            if (str.startsWith("\"")) {
                str = str.substring(1);
            }
            if (str.endsWith("\"")) {
                str = str.substring(0, str.length() - 1);
            }
            if (str.equals("Translate failed !")) {
                str = "";
            }
            return str;
        }).filter(StringUtils::isNotBlank).collect(Collectors.toList());
        if (!list.isEmpty()) {
            return new Msg(1, list);
        } else {
            return new Msg(0);
        }
    }


    private Lang getLang(String langString) {
        Lang lang = null;
        if (StringUtils.isNotBlank(langString)) {
            langString = langString.trim().toUpperCase();
            if (langString.equals("JA")) {
                langString = "JP";
            }
            if (langString.equals("FR")) {
                langString = "FRA";
            }
            if (langString.equals("ES")) {
                langString = "SPA";
            }
            if (langString.equals("KO")) {
                langString = "KOR";
            }
            if (langString.equals("VI")) {
                langString = "VIE";
            }
            if (langString.equals("AR")) {
                langString = "ARA";
            }
            if (langString.equals("DA")) {
                langString = "DAN";
            }
            try {
                lang = Lang.valueOf(langString);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return lang;
    }
}
