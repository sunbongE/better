package com.better.better.util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author 익산 박태호
 * @apiNote 공통으로 사용될 데이터 검사 기능
 */
public class BetterUtil {


    /**
     * 공백을 제외한 문자열 null 혹은 공백 확인
     * java11부터 isBlank 지원
     * @param input 사용자가 입력한 data
     * @return 값이 유효하면 true 아니면 false 반환
     */
    public static boolean isNotNull(String input) {
        if ( input== null || input.isBlank()) {
            return false;
        }
        return true;
    }

    /**
     * script 만 replace 처리한다.
     *
     * @param input 사용자가 입력한 data
     * @return script 치환된 문자열
     */
    public static String replaceScriptOnly(String input) {
        String str = input;
        if (str != null && !str.isBlank()) {
            str = str.replaceAll("<+[S,s][C,c][R,r][I,i][P,p][T,t]", "&lt;script")
                    .replaceAll("<+/+[S,s][C,c][R,r][I,i][P,p][T,t]", "&lt;/script");
        }
        return str;
    }




    public static String getIP(HttpServletRequest request){
        String[] headerList = {
                "X-FORWARDED-FOR",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "HTTP_VIA",
                "REMOTE_ADDR"
        };

        for (String header : headerList) {
            String ip = request.getHeader(header);
            if(ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }

        // ip를 찾을 수 없는 경우.
        return request.getRemoteAddr();
    }



    /**
     * Cross Site Script 예방
     *
     * @param input 사용자가 입력한 data
     * @return 변환된 문자열
     */
    public static String replaceCSS(String input) {
        String str = input;
        if (str != null && !"".equals(str)) {
            return str;
        }
        str = str.replaceAll("<!--StartFragment-->", ""); // MS Word 복사 시 삽입되는 불필요한 주석 제거

        // iframe 태그 방지
        str = str.replaceAll("<+[I,i][F,f][R,r][A,a][M,m][E,e]", "&lt;frame") // <iframe → &lt;frame으로 치환
                .replaceAll("<+/+[I,i][F,f][R,r][A,a][M,m][E,e]", "&lt;/frame"); // </iframe → &lt;/frame으로 치환

        // object 태그 방지
        str = str.replaceAll("<+[O,o][B,b][J,j][E,e][C,c][T,t]", "&lt;object") // <object → &lt;object
                .replaceAll("<+/+[O,o][B,b][J,j][E,e][C,c][T,t]", "&lt;/object"); // </object → &lt;/object

        // script 태그 방지
        str = str.replaceAll("<+[S,s][C,c][R,r][I,i][P,p][T,t]", "&lt;script") // <script → &lt;script
                .replaceAll("<+/+[S,s][C,c][R,r][I,i][P,p][T,t]", "&lt;/script"); // </script → &lt;/script

        // style 태그 방지 (스타일 인젝션 방지)
        str = str.replaceAll("<+[S,s][T,t][Y,y][L,l][E,e]", "&lt;style") // <style → &lt;style
                .replaceAll("<+/+[S,s][T,t][Y,y][L,l][E,e]", "&lt;/style"); // </style → &lt;/style

        // meta 태그 방지 (메타 태그를 통한 리디렉션 방지 등)
        str = str.replaceAll("<+[M,m][E,e][T,t][A,a]", "&lt;meta") // <meta → &lt;meta
                .replaceAll("<+/+[M,m][E,e][T,t][A,a]", "&lt;/meta"); // </meta → &lt;/meta

        // 유니코드 엔티티 기반 공백 제거 (탭, 줄바꿈 등 우회 문자 제거)
        str = str.replaceAll("&#x09;", "") // 수평 탭 제거
                .replaceAll("&#x0A;", "") // 줄바꿈 제거
                .replaceAll("&#x0D;", ""); // 캐리지 리턴 제거

        // 자바스크립트 실행 우회코드 제거 (유니코드, 아스키코드 등으로 인코딩된 javascript:alert('XSS') 제거)
        str = str.replaceAll("&#x6A&#x61&#x76&#x61&#x73&#x63&#x72&#x69&#x70&#x74&#x3A&#x61&#x6C&#x65&#x72&#x74&#x28&#x27&#x58&#x53&#x53&#x27&#x29", "") // "javascript:alert('XSS')"의 16진수 인코딩 제거
                .replaceAll("&#106;&#97;&#118;&#97;&#115;&#99;&#114;&#105;&#112;&#116;&#58;&#97;&#108;&#101;&#114;&#116;&#40;&#39;&#88;&#83;&#83;&#39;&#41;", "") // 같은 내용을 10진수로 인코딩한 코드 제거
                .replaceAll("&#0000106&#0000097&#0000118&#0000097&#0000115&#0000099&#0000114&#0000105&#0000112&#0000116&#0000058&#0000097&#0000108&#0000101&#0000114&#0000116&#0000040&#0000039&#0000088&#0000083&#0000083&#0000039&#0000041", ""); // 0이 포함된 10진수 표현 제거

        // "javascript:" 키워드 우회 입력 제거
        str = str.replaceAll("&#x6A", "") // &#x6A = j 제거
                .replaceAll("&#x61", "") // &#x61 = a 제거
                .replaceAll("&#0000106", "") // &#0000106 = j 제거
                .replaceAll("&#0000097", "") // &#0000097 = a 제거
                .replaceAll("&#106;", "") // &#106 = j 제거
                .replaceAll("&#97;", ""); // &#97 = a 제거

        // 일반 탭 문자 제거
        str = str.replaceAll("[\t]", ""); // 탭 제거

        // CSS 주석 제거 (/* */를 이용한 우회 제거)
        str = str.replaceAll("/\\*.*\\*/", ""); // /* ... */ 패턴 제거

        // expression() CSS 함수 호출 방지 (XSS 우회기법 중 하나)
        str = str.replaceAll("(?i)expression", "e-x-p-r-e-s-s-i-o-n"); // expression → e-x-p-r-e-s-s-i-o-n

        // javascript, vbscript 프로토콜 차단
        str = str.replaceAll("(?i)javascript", "j-v-a-s-c-r-i-p-t") // javascript → j-v-a-s-c-r-i-p-t
                .replaceAll("(?i)vbscript", "v-b-s-c-r-i-p-t"); // vbscript → v-b-s-c-r-i-p-t

        // onXXX 이벤트 핸들러 우회 방지 (모두 0nXXX로 변환)
        str = str.replaceAll("(?i)onabort", "0nabort") // onabort → 0nabort
                .replaceAll("(?i)onactivate", "0nactivate") // onactivate → 0nactivate
                .replaceAll("(?i)onafter", "0nafter") // onafterprint 등 방지
                .replaceAll("(?i)onbefore", "0nbefore") // onbeforeunload 등 방지
                .replaceAll("(?i)onblur", "0nblur") // onblur → 0nblur
                .replaceAll("(?i)onbounce", "0nbounce") // onbounce → 0nbounce
                .replaceAll("(?i)oncellchange", "0ncellchange") // oncellchange → 0ncellchange
                .replaceAll("(?i)onchange", "0nchange") // onchange → 0nchange
                .replaceAll("(?i)onclick", "0nclick") // onclick → 0nclick
                .replaceAll("(?i)oncontextmenu", "0ncontextmenu") // 우클릭 이벤트 방지
                .replaceAll("(?i)oncontrolselect", "0ncontrolselect") // oncontrolselect 방지
                .replaceAll("(?i)oncopy", "0ncopy") // 복사 이벤트 방지
                .replaceAll("(?i)oncut", "0ncut") // 잘라내기 이벤트 방지
                .replaceAll("(?i)ondataavailable", "0ndataavailable") // data available 이벤트 방지
                .replaceAll("(?i)ondataset", "0ndataset") // ondatasetchanged 등 방지
                .replaceAll("(?i)ondblclick", "0ndblclick") // 더블 클릭 이벤트 방지
                .replaceAll("(?i)ondeactivate", "0ndeactivate") // 포커스 해제 이벤트 방지
                .replaceAll("(?i)ondrag", "0ndrag") // 드래그 이벤트 방지
                .replaceAll("(?i)ondrop", "0ndrop") // 드롭 이벤트 방지
                .replaceAll("(?i)onerror", "0nerror") // 이미지 등 에러 핸들러 방지
                .replaceAll("(?i)onfilterchange", "0nfilterchange") // IE 필터 이벤트 방지
                .replaceAll("(?i)onfinish", "0nfinish") // onfinish → 0nfinish
                .replaceAll("(?i)onfocus", "0nfocus") // 포커스 이벤트 방지
                .replaceAll("(?i)onhelp", "0nhelp") // 도움말 핸들러 방지
                .replaceAll("(?i)onkeydown", "0nkeydown") // 키보드 입력 방지
                .replaceAll("(?i)onkeypress", "0nkeypress") // 키프레스 방지
                .replaceAll("(?i)onkeyup", "0nkeyup") // 키업 이벤트 방지
                .replaceAll("(?i)onload", "0nload") // onload 이벤트 방지
                .replaceAll("(?i)onlosecapture", "0nlosecapture") // 포커스 잃음 방지
                .replaceAll("(?i)onmouse", "0nmouse") // 마우스 관련 이벤트 (mouseover 등 포함) 방지
                .replaceAll("(?i)onmove", "0nmove") // 이동 이벤트 방지
                .replaceAll("(?i)onpaste", "0npaste") // 붙여넣기 이벤트 방지
                .replaceAll("(?i)onpropertychange", "0npropertychange") // 속성 변경 감지 이벤트 방지
                .replaceAll("(?i)onreadystatechange", "0nreadystatechange") // ready 상태 감지 방지
                .replaceAll("(?i)onreset", "0nreset") // 폼 리셋 방지
                .replaceAll("(?i)onresize", "0nresize") // 리사이즈 이벤트 방지
                .replaceAll("(?i)onrow", "0nrow") // onrowenter 등 방지
                .replaceAll("(?i)onscroll", "0nscroll") // 스크롤 이벤트 방지
                .replaceAll("(?i)onselect", "0nselect") // 선택 이벤트 방지
                .replaceAll("(?i)onselectionchange", "0nselectionchange") // 선택 변경 방지
                .replaceAll("(?i)onselectstart", "0nselectstart") // 선택 시작 이벤트 방지
                .replaceAll("(?i)onstart", "0nstart") // 미디어 시작 등 방지
                .replaceAll("(?i)onstop", "0nstop") // onstop → 0nstop
                .replaceAll("(?i)onsubmit", "0nsubmit") // 폼 전송 이벤트 방지
                .replaceAll("(?i)onunload", "0nunload"); // 페이지 언로드 이벤트 방지
        return str;
    }
}
