<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<a id="btn" href="javascript:void(0)" class="easyui-linkbutton"
	data-options="iconCls:'icon-search'">执行导入</a>


<script type="text/javascript">
	$(function() {
		$('#btn').bind('click', function() {
			$.post("/search/manager/importall", null, function(data) {
				if (data.status == 200) {
					$.messager.alert('提示', '导入索引成功!');
				} else if (data.status == 500) {
					$.messager.alert(data.msg);
				}

			});
		});
	});
</script>