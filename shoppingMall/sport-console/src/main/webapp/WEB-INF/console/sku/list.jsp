<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>babasport-list</title>
</head>
<body>
<script>
	function updateSku(id){
		$('#m'+id).attr("disabled",'');
		$('#p'+id).attr("disabled",'');
		$('#s'+id).attr("disabled",'');
		$('#u'+id).attr("disabled",'');
		$('#d'+id).attr("disabled",'');
	}
	function addSku(id){
		$.post('/updateSku.do', $('#tableForm'+id).serialize(), function (data) { 
		
		}, 'json');
		$('#m'+id).attr("disabled",'disabled');
		$('#p'+id).attr("disabled",'disabled');
		$('#s'+id).attr("disabled",'disabled');
		$('#u'+id).attr("disabled",'disabled');
		$('#d'+id).attr("disabled",'disabled');
		
	} 
</script>
<div class="box-positon">
	<div class="rpos">当前位置: 库存管理 - 列表</div>
	<div class="clear"></div>
</div>
<div class="body-box">

<table cellspacing="1" cellpadding="0" border="0" width="100%" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="20"><input type="checkbox" onclick="Pn.checkbox('ids',this.checked)"/></th>
			<th>商品编号</th>
			<th>商品颜色</th>
			<th>商品尺码</th>
			<th>市场价格</th>
			<th>销售价格</th>
			<th>库       存</th>
			<th>购买限制</th>
			<th>运       费</th>
			<th>是否赠品</th>
			<th>操       作</th>
		</tr>
	</thead>
	<tbody class="pn-ltbody">
		<c:forEach items="${slist }" var="s">
		<form method="post" id="tableForm${s.id }" action="updateSku.do">
			<tr bgcolor="#ffffff" onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
				<td><input type="checkbox" name="ids" value="73"/></td>
				<td>20141028114308048<input type="hidden" name="id" id="id" value="${s.id }"/></td>
				<td align="center"><c:forEach items="${clist }" var="c"> <c:if test="${c.id==s.colorId }">${c.name }</c:if> </c:forEach></td>
				<td align="center">${s.size }</td>
				<td align="center"><input type="text" id="m${s.id }" name="marketPrice" value="${s.marketPrice }" disabled="disabled" size="10"/></td>
				<td align="center"><input type="text" id="p${s.id }" name="price" value="${s.price }" disabled="disabled" size="10"/></td>
				<td align="center"><input type="text" id="s${s.id }" name="stock" value="${s.stock }" disabled="disabled" size="10"/></td>
				<td align="center"><input type="text" id="u${s.id }" name="upperLimit" value="${s.upperLimit }" disabled="disabled" size="10"/></td>
				<td align="center"><input type="text" id="d${s.id }" name="deliveFee" value="${s.deliveFee }" disabled="disabled" size="10"/></td>
				<td align="center">否<input type="hidden" name="productId" id="productId" value="${s.productId }"/></td>
				<td align="center"><a href="javascript:updateSku(${s.id })" class="pn-opt"><input type="button" value="修改"/></a> | <a href="javascript:addSku(${s.id });" class="pn-opt"><input type="button" value="保存"/></a></td>
			</tr>
			</form>
		</c:forEach>
	</tbody>
</table>

</div>
</body>
</html>