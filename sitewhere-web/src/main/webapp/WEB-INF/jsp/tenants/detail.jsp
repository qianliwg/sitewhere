<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="sitewhere_title" value="View Tenant" />
<c:set var="sitewhere_section" value="tenants" />
<c:set var="use_highlight" value="true" />
<%@ include file="../includes/top.inc"%>

<!-- Title Bar -->
<div class="sw-title-bar content k-header" style="margin-bottom: 15px;">
	<h1 class="ellipsis" data-i18n="tenant.detail.title">View Tenant</h1>
	<div class="sw-title-bar-right">
		<a id="btn-refresh-tenant" class="btn" href="javascript:void(0)"> <i
			class="fa fa-refresh sw-button-icon"></i> <span data-i18n="public.Refresh">Refresh</span>
		</a>
	</div>
</div>

<div id="tenant-details" style="line-height: normal;"></div>

<!-- Tab panel -->
<div id="tabs">
	<ul>
		<li class="k-state-active">Engine Details<font data-i18n="tenant.detail.EngineDetails"></font></li>
		<li>Engine Configuration<font data-i18n="tenant.detail.EngineConfiguration"></font></li>
	</ul>
	<div>
		<div id="detail-content"></div>
	</div>
	<div>
		<div style="max-height: 500px; overflow-y: scroll;">
			<pre class="language-markup" style="background-color: #fff;">
				<code id="config-content"></code>
			</pre>
		</div>
	</div>
</div>

<form id="view-tenant-list" method="get"></form>

<%@ include file="tenantCreateDialog.inc"%>
<%@ include file="tenantEntry.inc"%>

<!-- Details panel shown for a started engine -->
<script type="text/x-kendo-tmpl" id="tpl-engine-started">
	<div>
		<div id="tenant-engine-hierarchy" style="margin-top: 10px; margin-bottom: 10px;"></div>
	</div>
</script>

<!-- Details panel shown for a stopped engine -->
<script type="text/x-kendo-tmpl" id="tpl-engine-stopped">
	<div style="text-align: center; font-size: 26px; padding: 50px;">
		<i class="fa fa-power-off sw-button-icon" style="color: \\#ccc;"></i> Tenant Engine is Stopped
	</div>
</script>

<!-- Details panel shown for a engine in other non-running states -->
<script type="text/x-kendo-tmpl" id="tpl-engine-not-running">
	<div style="text-align: center; font-size: 26px; padding: 50px;">
		<i class="fa fa-power-off sw-button-icon" style="color: \\#ccc;"></i> Tenant Engine is Not Running
	</div>
</script>

<script>
	var tenantId = '<c:out value="${selected.id}"/>';

	/** Tenant information */
	var tenant;

	/** Tabs */
	var tabs;

	/** Called when delete button is clicked */
	function onDeleteClicked() {
		swConfirm("Delete Tenant", "Are you sure you want to delete tenant '" + tenantId + "'?", function(
				result) {
			if (result) {
				$.deleteJSON("${pageContext.request.contextPath}/api/tenants/" + tenantId
						+ "?force=true&tenantAuthToken=${tenant.authenticationToken}", onDeleteSuccess,
					onDeleteFail);
			}
		});
	}

	/** Called on successful delete */
	function onDeleteSuccess() {
		$("#view-tenant-list").attr("action", "${pageContext.request.contextPath}/admin/tenants/list.html");
		$('#view-tenant-list').submit();
	}

	/** Handle failed delete call */
	function onDeleteFail(jqXHR, textStatus, errorThrown) {
		handleError(jqXHR, "Unable to delete tenant.");
	}

	/** Called when edit button is clicked */
	function onEditClicked() {
		tuOpen(tenantId, onEditSuccess);
	}

	/** Called on successful edit */
	function onEditSuccess() {
		loadTenant();
	}

	/** Called when stop button is clicked */
	function onTenantStopClicked() {
		$.postJSON("${pageContext.request.contextPath}/api/tenants/" + tenantId
				+ "/engine/stop?tenantAuthToken=${tenant.authenticationToken}", null, commandSuccess,
			stopFailed);
	}

	function commandSuccess(data, status, jqXHR) {
		loadTenant();
	}

	function stopFailed(jqXHR, textStatus, errorThrown) {
		handleError(jqXHR, "Unable to process engine stop command.");
	}

	/** Called when start button is clicked */
	function onTenantStartClicked() {
		$.postJSON("${pageContext.request.contextPath}/api/tenants/" + tenantId
				+ "/engine/start?tenantAuthToken=${tenant.authenticationToken}", null, commandSuccess,
			startFailed);
	}

	function startFailed(jqXHR, textStatus, errorThrown) {
		handleError(jqXHR, "Unable to process engine start command.");
	}

	$(document).ready(function() {
		/** Handle refresh button */
		$('#btn-refresh-tenant').click(function(event) {
			loadTenant();
		});

		/** Create the tab strip */
		tabs = $("#tabs").kendoTabStrip({
			animation : false,
		}).data("kendoTabStrip");

		loadTenant();
	});

	/** Loads information for the selected tenant */
	function loadTenant() {
		$.getJSON("${pageContext.request.contextPath}/api/tenants/" + tenantId
				+ "?includeRuntimeInfo=true&tenantAuthToken=${tenant.authenticationToken}", loadGetSuccess,
			loadGetFailed);
	}

	/** Called on successful tenant load request */
	function loadGetSuccess(data, status, jqXHR) {
		tenant = data;
		var template = kendo.template($("#tpl-tenant-entry").html());
		data.inDetailView = true;
		$('#tenant-details').html(template(data));

		if (tenant.engineState) {
			if (data.engineState.lifecycleStatus == 'Started') {
				$('#tenant-power-off').show();
				$('#tenant-power-on').hide();
				$('#tenant-edit').hide();
				$('#tenant-delete').hide();
				template = kendo.template($("#tpl-engine-started").html());
				$('#detail-content').html(template(data));
				loadEngineHierarchy(data);
				loadEngineConfiguration();
			} else if (data.engineState.lifecycleStatus == 'Stopped') {
				$('#tenant-power-off').hide();
				$('#tenant-power-on').show();
				$('#tenant-edit').show();
				$('#tenant-delete').show();
				template = kendo.template($("#tpl-engine-stopped").html());
				$('#detail-content').html(template(data));
			} else {
				$('#tenant-power-off').hide();
				$('#tenant-power-on').hide();
				$('#tenant-edit').hide();
				$('#tenant-delete').hide();
				template = kendo.template($("#tpl-engine-not-running").html());
				$('#detail-content').html(template(data));
			}
		} else {
			$('#tenant-power-off').hide();
			$('#tenant-power-on').show();
			$('#tenant-edit').show();
			$('#tenant-delete').show();
			template = kendo.template($("#tpl-engine-not-running").html());
			$('#detail-content').html(template(data));
		}
	}

	/** Handle error on getting tenant data */
	function loadGetFailed(jqXHR, textStatus, errorThrown) {
		handleError(jqXHR, "Unable to load tenant data.");
	}

	/** Load engine hierarchy into tree */
	function loadEngineHierarchy(engine) {
		var dataSource = new kendo.data.TreeListDataSource({
			data : engine.engineState.componentHierarchyState,
			schema : {
				model : {
					id : "id",
					expanded : true
				}
			}
		});

		$("#tenant-engine-hierarchy").kendoTreeList({
			dataSource : dataSource,
			height : 500,
			columns : [ {
				field : "name",
				title : "Component Name",
				width : 400
			}, {
				field : "type",
				title : "Type",
				width : 150
			}, {
				field : "status",
				title : "Status",
				width : 150
			} ]
		});
	}

	/** Load the running engine configuration */
	function loadEngineConfiguration() {
		$.getJSON("${pageContext.request.contextPath}/api/tenants/" + tenantId
				+ "/engine/configuration?tenantAuthToken=${tenant.authenticationToken}", configGetSuccess,
			configGetFailed);
	}

	/** Called on successful configuration load request */
	function configGetSuccess(data, status, jqXHR) {
		$("#config-content").text(data);
		Prism.highlightElement(document.getElementById('config-content'));
	}

	/** Handle error on getting configuration data */
	function configGetFailed(jqXHR, textStatus, errorThrown) {
		handleError(jqXHR, "Unable to load tenant configuration.");
	}
</script>

<%@ include file="../includes/bottom.inc"%>