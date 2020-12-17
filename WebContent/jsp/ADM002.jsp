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

    <!-- Begin vung dieu kien tim kiem -->
    <form action="listUser.web?type=search" method="post" name="mainform">
        <table class="tbl_input" border="0" width="90%" cellpadding="0"
            cellspacing="0">
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>会員名称で会員を検索します。検索条件無しの場合は全て表示されます。</td>
            </tr>
            <tr>
                <td width="100%">
                    <table class="tbl_input" cellpadding="4" cellspacing="0">
                        <tr>
                            <td class="lbl_left">氏名:</td>
                            <td align="left"><input class="txBox" type="text"
                                name="name"
                                value="<c:out value="${searchName}" escapeXml="true"/>"
                                size="20" onfocus="this.style.borderColor='#0066ff';"
                                onblur="this.style.borderColor='#aaaaaa';" /></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="lbl_left">グループ:</td>
                            <td align="left" width="80px"><select name="group_id">
                                    <option value="0">全て</option>
                                    <c:forEach items="${listGroup}" var="group">
                                        <c:choose>
                                            <c:when test="${group.groupId == searchGroupId}">
                                                <option value="${group.groupId}" selected="selected">${group.groupName}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${group.groupId}">${group.groupName}</option>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:forEach>
                            </select></td>
                            <td align="left"><input class="btn" type="submit" value="検索" />
                                <input class="btn" type="button" value="新規追加"
                                onclick="goTo('addUserInput.web?type=add')" /></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <!-- End vung dieu kien tim kiem -->
    </form>
    <!-- Begin vung hien thi danh sach user -->
    <table class="tbl_list" border="1" cellpadding="4" cellspacing="0"
        width="80%">
        <c:choose>
            <c:when test="${fn:length(listUser) == 0}">
                <tr>
                    <td colspan=9 style="color: red; font-size: 20px" align="center">検索条件に該当するユーザが見つかりません。</td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr class="tr2">
                    <th align="center" width="20px">ID</th>
                    <c:choose>
                        <c:when test="${listSortType[0] == 'ASC'}">
                            <th align="left">氏名 <a
                                href="listUser.web?type=sort&order=full_name" name="sort">▲▽</a>
                            </th>
                        </c:when>
                        <c:otherwise>
                            <th align="left">氏名 <a
                                href="listUser.web?type=sort&order=full_name" name="sort">△▼</a>
                            </th>
                        </c:otherwise>
                    </c:choose>

                    <th align="left">生年月日</th>
                    <th align="left">グループ</th>
                    <th align="left">メールアドレス</th>
                    <th align="left" width="70px">電話番号</th>

                    <c:choose>
                        <c:when test="${listSortType[1] == 'DESC'}">
                            <th align="left">日本語能力 <a
                                href="listUser.web?type=sort&order=code_level" name="sort">▲▽</a>
                            </th>
                        </c:when>
                        <c:otherwise>
                            <th align="left">日本語能力 <a
                                href="listUser.web?type=sort&order=code_level" name="sort">△▼</a>
                            </th>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${listSortType[2] == 'ASC'}">
                            <th align="left">失効日 <a
                                href="listUser.web?type=sort&order=end_date" name="sort">▲▽</a>
                            </th>
                        </c:when>
                        <c:otherwise>
                            <th align="left">失効日 <a
                                href="listUser.web?type=sort&order=end_date" name="sort">△▼</a>
                            </th>
                        </c:otherwise>
                    </c:choose>

                    <th align="left">点数</th>
                </tr>
            </c:otherwise>
        </c:choose>
        <c:forEach items="${listUser}" var="user">
            <tr>
                <td align="right"><a href="detailUser.web?userId=${user.userId}">${user.userId}</a></td>
                <td><c:out value="${user.fullName}" escapeXml="true" /></td>
                <td align="center">${user.birthdayString}</td>
                <td>${user.groupName}</td>
                <td><c:out value="${user.email}" escapeXml="true" /></td>
                <td>${user.tel}</td>
                <td>${user.nameLevel}</td>
                <td align="center">${user.endDateString}</td>
                <td align="right">${user.total}</td>
            </tr>
        </c:forEach>
    </table>
    <!-- End vung hien thi danh sach user -->

    <!-- Begin vung paging -->
    <table>
        <c:set var="lengthPaging" value="${fn:length(listPaging)}" />
        <c:if test="${lengthPaging >= 1}">
            <tr class="lbl_paging">
                <c:if test="${listPaging[0] != 1}">
                    <td><a
                        href="listUser.web?type=paging&page=${listPaging[0] - pageLimit}">&lt;&lt;</a>&nbsp;</td>
                </c:if>
                <c:forEach items="${listPaging}" var="page">
                    <c:if test="${page == currentPage}">
                        <td>${page}&nbsp;</td>
                    </c:if>
                    <c:if test="${page != currentPage}">
                        <td><a href="listUser.web?type=paging&page=${page}">${page}</a>&nbsp;</td>
                    </c:if>
                </c:forEach>
                <c:if test="${listPaging[lengthPaging - 1] < totalPage}">
                    <td><a
                        href="listUser.web?type=paging&page=${listPaging[lengthPaging - 1] + 1}">&gt;&gt;</a>&nbsp;</td>
                </c:if>
            </tr>
        </c:if>

    </table>
    <!-- End vung paging -->

    <!-- Begin vung footer -->
    <jsp:include page="footer.jsp" />
    <!-- End vung footer -->
</body>
</html>