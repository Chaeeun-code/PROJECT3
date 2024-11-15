<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
   <h1>상품등록</h1>
   <table  border="2">                           <!--파일 업로드가 필요할때 사용-->
       <form action="Insertinfo" method="post" enctype="multipart/form-data">
       <tr> <td > 이름 </td>
           <td> <input type="text" name="imageid"> </td></tr>
       <tr><td> 사진 </td>
           <td> <input type="file" name="uploadFile"> </td></tr>
        <tr>  <td colspan="2"> <input type="submit" value="등록"></tr>
       </form> 
   </table>  <br>
     
        <a href="getallinfo">[전체보기]</a>
</body>
</html>