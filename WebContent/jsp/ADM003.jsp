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
    <c:set var="uri"
        value="${requestScope['javax.servlet.forward.request_uri']}" />
    <c:choose>
        <c:when test="${fn:contains(uri, 'editUser.web')}">
            <c:set var="action"
                value="editUser.web?userId=${param.userId}&type=edit" />
            <c:set var="readonly" value="readonly" />
            <c:set var="caseInput" value="edit" />
        </c:when>
        <c:otherwise>
            <c:set var="action" value="addUserValidate.web?type=add" />
            <c:set var="readonly" value="" />
            <c:set var="caseInput" value="add" />
        </c:otherwise>
    </c:choose>
    <form action="${action}" method="post" name="inputform" id="inputform"
        onsubmit="createHiddenParam()">
        <table class="tbl_input" border="0" width="75%" cellpadding="0"
            cellspacing="0">
            <tr>
                <th align="left">
                    <div style="padding-left: 100px;">会員情報編集</div>
                </th>
            </tr>
            <c:forEach items="${listError}" var="error">
                <tr>
                    <td class="errMsg" style="padding-left: 120px" colspan="2"><c:out
                            value="${error}" escapeXml="true" /></td>
                </tr>
            </c:forEach>
            <tr>
                <td align="left">
                    <div style="padding-left: 100px;">
                        <table border="0" width="100%" class="tbl_input" cellpadding="4"
                            cellspacing="0">
                            <tr>
                                <td class="lbl_left"><font color="red">*</font> アカウント名:</td>
                                <td align="left"><input class="txBox" type="text"
                                    name="loginName"
                                    value="<c:out value="${userInfor.loginName}" escapeXml="true"/>"
                                    size="15" onfocus="this.style.borderColor='#0066ff';"
                                    onblur="this.style.borderColor='#aaaaaa';" ${readonly} /></td>
                            </tr>
                            <tr>
                                <td class="lbl_left"><font color="red">*</font> グループ:</td>
                                <td align="left"><select name="groupId" id="groupId">
                                        <c:forEach items="${listGroup}" var="group">
                                            <c:choose>
                                                <c:when test="${group.groupId == userInfor.groupId}">
                                                    <option value="${group.groupId}" selected="selected">${group.groupName}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${group.groupId}">${group.groupName}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                </select> <span>&nbsp;&nbsp;&nbsp;</span></td>
                            </tr>
                            <tr>
                                <td class="lbl_left"><font color="red">*</font> 氏名:</td>
                                <td align="left"><input class="txBox" type="text"
                                    name="name"
                                    value="<c:out value="${userInfor.fullName}" escapeXml="true"/>"
                                    size="30" onfocus="this.style.borderColor='#0066ff';"
                                    onblur="this.style.borderColor='#aaaaaa';" /></td>
                            </tr>
                            <tr>
                                <td class="lbl_left">カタカナ氏名:</td>
                                <td align="left"><input class="txBox" type="text"
                                    name="nameKana"
                                    value="<c:out value="${userInfor.fullNameKana}" escapeXml="true"/>"
                                    size="30" onfocus="this.style.borderColor='#0066ff';"
                                    onblur="this.style.borderColor='#aaaaaa';" /></td>
                            </tr>
                            <tr>
                                <td class="lbl_left"><font color="red">*</font> 生年月日:</td>
                                <td align="left"><select name="birthday">
                                        <c:forEach items="${listYears}" var="year">
                                            <c:choose>
                                                <c:when test="${year == userInfor.birthday[0]}">
                                                    <option value="${year}" selected="selected">${year}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${year}">${year}</option>
                                                </c:otherwise>
                                            </c:choose>

                                        </c:forEach>
                                </select>年 <select name="birthday">
                                        <c:forEach items="${listMonths}" var="month">
                                            <c:choose>
                                                <c:when test="${month == userInfor.birthday[1]}">
                                                    <option value="${month}" selected="selected">${month}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${month}">${month}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                </select>月 <select name="birthday">
                                        <c:forEach items="${listDays}" var="day">
                                            <c:choose>
                                                <c:when test="${day == userInfor.birthday[2]}">
                                                    <option value="${day}" selected="selected">${day}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${day}">${day}</option>
                                                </c:otherwise>
                                            </c:choose>

                                        </c:forEach>
                                </select>日</td>
                            </tr>
                            <tr>
                                <td class="lbl_left"><font color="red">*</font> メールアドレス:</td>
                                <td align="left"><input class="txBox" type="text"
                                    name="email"
                                    value="<c:out value="${userInfor.email}" escapeXml="true"/>"
                                    size="30" onfocus="this.style.borderColor='#0066ff';"
                                    onblur="this.style.borderColor='#aaaaaa';" /></td>
                            </tr>
                            <tr>
                                <td class="lbl_left"><font color="red">*</font>電話番号:</td>
                                <td align="left"><input class="txBox" type="text"
                                    name="tel"
                                    value="<c:out value="${userInfor.tel}" escapeXml="true"/>"
                                    size="30" onfocus="this.style.borderColor='#0066ff';"
                                    onblur="this.style.borderColor='#aaaaaa';" /></td>
                            </tr>
                            <c:choose>
                                <c:when test="${caseInput eq 'edit'}">
                                    <tr>
                                        <td align="left"><input class="txBox" type="hidden"
                                            name="userId"
                                            value="<c:out value="${param.userId}" escapeXml="true"/>"
                                            size="30" /></td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td class="lbl_left"><font color="red">*</font> パスワード:</td>
                                        <td align="left"><input class="txBox" type="password"
                                            name="password"
                                            value="<c:out value="${userInfor.password}" escapeXml="true"/>"
                                            size="30" onfocus="this.style.borderColor='#0066ff';"
                                            onblur="this.style.borderColor='#aaaaaa';" /></td>
                                    </tr>
                                    <tr>
                                        <td class="lbl_left">パスワード（確認）:</td>
                                        <td align="left"><input class="txBox" type="password"
                                            name="passwordConfirm"
                                            value="<c:out value="${userInfor.passwordConfirm}" escapeXml="true"/>"
                                            size="30" onfocus="this.style.borderColor='#0066ff';"
                                            onblur="this.style.borderColor='#aaaaaa';" /></td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>

                            <tr>
                                <th align="left" colspan="2"><a href="#"
                                    onclick="showOrHideElements('mstJapan')">日本語能力</a></th>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="left">
                    <div id="mstJapan" style="padding-left: 100px; display: none">
                        <table border="0" width="100%" class="tbl_input" cellpadding="4"
                            cellspacing="0">
                            <tr>
                                <td class="lbl_left">資格:</td>
                                <td align="left"><select name="codeLevel" id="codeLevel">
                                        <c:forEach items="${listMstJapan}" var="mstJapan">
                                            <c:choose>
                                                <c:when test="${mstJapan.codeLevel == userInfor.codeLevel}">
                                                    <option value="${mstJapan.codeLevel}" selected="selected">${mstJapan.nameLevel}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${mstJapan.codeLevel}">${mstJapan.nameLevel}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                </select></td>
                            </tr>
                            <tr>
                                <td class="lbl_left">資格交付日:</td>
                                <td align="left"><select name="startDate">
                                        <c:forEach items="${listYears}" var="year">
                                            <c:choose>
                                                <c:when test="${year == userInfor.startDate[0]}">
                                                    <option value="${year}" selected="selected">${year}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${year}">${year}</option>
                                                </c:otherwise>
                                            </c:choose>

                                        </c:forEach>
                                </select>年 <select name="startDate">
                                        <c:forEach items="${listMonths}" var="month">
                                            <c:choose>
                                                <c:when test="${month == userInfor.startDate[1]}">
                                                    <option value="${month}" selected="selected">${month}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${month}">${month}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                </select>月 <select name="startDate">
                                        <c:forEach items="${listDays}" var="day">
                                            <c:choose>
                                                <c:when test="${day == userInfor.startDate[2]}">
                                                    <option value="${day}" selected="selected">${day}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${day}">${day}</option>
                                                </c:otherwise>
                                            </c:choose>

                                        </c:forEach>
                                </select>日</td>
                            </tr>
                            <tr>
                                <td class="lbl_left">失効日:</td>
                                <td align="left"><select name="endDate">
                                        <!-- Start fix bug ID 59,60 – LeThanhPhu 2017/02/22 -->
                                        <c:forEach items="${listYearsEndDate}" var="year">
                                            <!-- End fix bug ID 59,60 – LeThanhPhu 2017/02/22 -->
                                            <c:choose>
                                                <c:when test="${year == userInfor.endDate[0]}">
                                                    <option value="${year}" selected="selected">${year}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${year}">${year}</option>
                                                </c:otherwise>
                                            </c:choose>

                                        </c:forEach>
                                </select>年 <select name="endDate">
                                        <c:forEach items="${listMonths}" var="month">
                                            <c:choose>
                                                <c:when test="${month == userInfor.endDate[1]}">
                                                    <option value="${month}" selected="selected">${month}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${month}">${month}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                </select>月 <select name="endDate">
                                        <c:forEach items="${listDays}" var="day">
                                            <c:choose>
                                                <c:when test="${day == userInfor.endDate[2]}">
                                                    <option value="${day}" selected="selected">${day}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${day}">${day}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                </select>日</td>
                            </tr>
                            <tr>
                                <td class="lbl_left">点数:</td>
                                <td align="left"><input class="txBox" type="text"
                                    name="total"
                                    value="<c:out value="${userInfor.total}" escapeXml="true"/>"
                                    size="5" onfocus="this.style.borderColor='#0066ff';"
                                    onblur="this.style.borderColor='#aaaaaa';" /></td>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>
        </table>

        <div style="padding-left: 100px;">&nbsp;</div>
        <!-- Begin vung button -->
        <div style="padding-left: 45px;">
            <table border="0" cellpadding="4" cellspacing="0" width="300px">
                <tr>
                    <th width="200px" align="center">&nbsp;</th>
                    <td><input class="btn" type="submit" value="確認" /></td>
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