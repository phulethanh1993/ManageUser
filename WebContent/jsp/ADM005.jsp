<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value="/css/style.css"/>" rel="stylesheet"
    type="text/css" />
<script type="text/javascript" src="<c:url value="/js/user.js"/>"></script>
<title>ユーザ管理</title>
</head>
<body>
    <!-- Begin vung header -->
    <jsp:include page="header.jsp" />
    <!-- End vung header -->

    <!-- Begin vung input-->
    <form action="deleteUser.web?userId=${param.userId}" method="post"
        name="inputform">
        <table class="tbl_input" border="0" width="75%" cellpadding="0"
            cellspacing="0">
            <tr>
                <th align="left">
                    <div style="padding-left: 100px;">情報確認</div>
                    <div style="padding-left: 100px;">&nbsp;</div>
                </th>
            </tr>
            <tr>
                <td align="left">
                    <div style="padding-left: 100px;">
                        <table border="1" width="70%" class="tbl_input" cellpadding="4"
                            cellspacing="0">
                            <tr>
                                <td class="lbl_left">アカウント名:</td>
                                <td align="left"><c:out value="${userInfor.loginName}"
                                        escapeXml="true" /></td>
                            </tr>
                            <tr>
                                <td class="lbl_left">グループ:</td>
                                <td align="left"><c:out value="${userInfor.groupName}"
                                        escapeXml="true" /></td>
                            </tr>
                            <tr>
                                <td class="lbl_left">氏名:</td>
                                <td align="left"><c:out value="${userInfor.fullName}"
                                        escapeXml="true" /></td>
                            </tr>
                            <tr>
                                <td class="lbl_left">カタカナ氏名:</td>
                                <td align="left"><c:out value="${userInfor.fullNameKana}"
                                        escapeXml="true" /></td>
                            </tr>
                            <tr>
                                <td class="lbl_left">生年月日:</td>
                                <td align="left"><c:out value="${userInfor.birthdayString}"
                                        escapeXml="true" /></td>
                            </tr>
                            <tr>
                                <td class="lbl_left">メールアドレス:</td>
                                <td align="left"><c:out value="${userInfor.email}"
                                        escapeXml="true" /></td>
                            </tr>
                            <tr>
                                <td class="lbl_left">電話番号:</td>
                                <td align="left"><c:out value="${userInfor.tel}"
                                        escapeXml="true" /></td>
                            </tr>
                            <tr>
                                <th colspan="2"><a href="#"
                                    onclick="showOrHideElements('mstJapan')">日本語能力</a></th>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="left">
                    <div id="mstJapan" style="padding-left: 100px; display: none">
                        <table border="1" width="70%" class="tbl_input" cellpadding="4"
                            cellspacing="0">
                            <tr>
                            <tr>
                                <td class="lbl_left">資格:</td>
                                <td align="left"><c:out value="${userInfor.nameLevel}"
                                        escapeXml="true" /></td>
                            </tr>
                            <tr>
                                <td class="lbl_left">資格交付日:</td>
                                <td align="left"><c:out
                                        value="${userInfor.startDateString}" escapeXml="true" /></td>
                            </tr>
                            <tr>
                                <td class="lbl_left">失効日:</td>
                                <td align="left"><c:out value="${userInfor.endDateString}"
                                        escapeXml="true" /></td>
                            </tr>
                            <tr>
                                <td class="lbl_left">点数:</td>
                                <td align="left"><c:out value="${userInfor.total}"
                                        escapeXml="true" /></td>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>
        </table>
        <div style="padding-left: 100px;">&nbsp;</div>
        <!-- Begin vung button -->
        <div style="padding-left: 100px;">
            <table border="0" cellpadding="4" cellspacing="0" width="300px">
                <tr>
                    <th width="200px" align="center">&nbsp;</th>
                    <td><input class="btn" type="button" value="編集"
                        onclick="goTo('editUser.web?userId=${param.userId}&type=edit')" /></td>
                    <c:if test="${userInfor.role == 0}">
                        <td><input class="btn" type="submit" value="削除"
                            onclick="return confirm('削除しますが、よろしいでしょうか。');" /></td>
                    </c:if>
                    <td><input class="btn" type="button" value="戻る"
                        onclick="goTo('listUser.web?type=back')" /></td>
                </tr>
            </table>
        </div>
        <!-- End vung button -->
    </form>
    <!-- End vung input -->

    <!-- Begin vung footer -->
    <jsp:include page="footer.jsp" />
    <!-- End vung footer -->
</body>
</html>