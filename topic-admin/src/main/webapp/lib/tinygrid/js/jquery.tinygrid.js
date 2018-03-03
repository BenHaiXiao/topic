/*
 * name: jquery.tinygrid.js
 *
 * Copyright (c) 2009
 * 
 * $author: huang weijian(黄伟鉴)$
 * $Date: 2009/07/28 07:14:38 $
 * $Contact: alvin.huang.wj@gmail.com$
 * $Reference: flexigrid by Paulo P. Marinas (webplicity.net/flexigrid)
 */
 
/**
 * 一个小型/灵巧的基于jQuery的Grid控件. 
 * 
 * 目前只支持两级的标题.
 * 
 * @author huang weijian
 */
(function($) {
	if (!String.prototype.format) {
		String.prototype.format = function(params) {
			var args = params;
			return this.replace(/{(\d{1})}/g, function(index, index2) {
				return args[index2];
			});
		};
	}
	Array.prototype.contains = function(item){
	    return RegExp("\\b"+item+"\\b").test(this);
	};
	$.getGreaterNum = function(num1, num2) {
		try {
			num1 = parseInt(num1);
		} catch (e) {
			return num2;
		}
		try {
			num2 = parseInt(num2);
		} catch (e) {
			return num1;
		}
		return num1 > num2 ? num1 : num2;	
	};
	$.isArray = function(obj) {
    	return obj && obj instanceof Array || typeof obj == "array";
	};
	$.getTextFromHtml = function(html,escapeQuote) {
		var result = html;
		var htmlTest = /^[^<]*(<(.|\s)+>)[^>]*$/;
		if (htmlTest.test(html)) {
			result = $(html).text();
		}
		if (result) {
			if(escapeQuote){
				return result.replace(/"/g, '&quot;').replace(/'/g, "&#039;");
			}
			return result.replace(/"/g, '\"').replace(/'/g, "\'");
		}
		return result;
	};
	
	$.addTinygrid = function(placeholder, options) {	
		var $placeholder = $(placeholder);
		$placeholder.attr({cellPadding:0,cellSpacing:0,border:0}).removeAttr('width').removeAttr('height');
		
		/**
		 * tinygrid使用到的样式
		 */
		var classNames = {
			globalContainerClass : 'globalContainerClass',
			
			titleContainerClass : 'titleContainerClass', // 整个标题栏的标式
			titleClass : 'titleClass', // 标题的样式
			
			buttonContainerClass : 'buttonContainerClass', // 整个工具栏的样式
			innerContainer : 'innerContainer', // 工具栏的按钮区样式 -- 左对齐
			innerContainer4Right : 'innerContainerRight', // 工具栏的按钮区样式--右对齐
			toolbarItemClass : 'toolbarItemClass', 
			buttonItemClass : 'btn btn-sm btn-default', // 按钮的样式
			buttonSpanClass : 'grid-button',

			tableContainerClass : 'tableContainerClass', // 表格数据区域的样式
			tableClass : 'table table-striped table table-bordered table-hover',

			showDetailClass : 'show-detail',
			showDetailOpenClass : 'show-detail-open',
			showDetailCloseClass : 'show-detail-close',
			detailTdClass : 'grid-details',
			
			messageTipContainerClass : 'messageTipContainerClass', // 信息提示区域
			errorMessageTipClass : 'errorMessageTipClass', // 出错信息提示
			
			statisticsContainerClass : 'statisticsContainerClass', // 统计栏
			
			pagerContainerClass : 'pagerContainerClass', // 整个分页栏的样式
			
			editableClass : 'editableClass' // 可编辑输入框样式 
		};
		
		/**
		 * 该tinygrid控件的一些默认设置
		 */
		var defaults = {
			defaultPrecision : 2, // 默认的计算数值精度
			
			title : false,	
			showTitle : false, // 是否显示标题
			
			usepager : true, // 是否使用分页栏?
			cachePageData : false, // 是否缓存已加载页码的数据? 即分页时不重新加载
			autoload : true,
			
			showMessageTip : true,
			
			rowClickToDoTick : false,	//是否行触发单击进行勾选前面的checkbox/radio的操作？
			rowClickJustCheckOne : false, // 行触发单击进行勾选时, 是否只选中当前行而取消其它行的选中?
			
			buttons : false, // 格式: [{[id:'id1',] name:'标题', onpress:pressId1, disabled : true}
			colModel : [], // 列模型格式: [{header:'标题', dataIndex:'col_name' [field:'javabean_fieldname' align:'left', cellClass:'className', cellRenderer:function(val, trId)
							// 			, cellClass2:function({dataIndex:xxxx}, trId)
							//			, cellRenderer2:function({dataIndex:xxxx}, trId, [cellvalue]), rowClassRenderer:function(val, rowIndex),]
							//			, countRenderer:function(val)}, details:true]
			customizeButtonHtml : '', // 自定义的按钮HTML, 常用于信息提示(如状态图的说明等)
			
			url : false, // ajax url
			method : 'GET', // POST 还是 GET
			dataType : 'json', // 调用url后得到的数据格式
			appendGridRequestParams : true, // 追加grid默认的请求参数
			errormsg : '连接错误！',
			
			useTimeout : false, // 是否使用超时判断?
			timeout : 300, // 超时设置,单位为秒(s), 默认为: 5m
			
			page : 1, // 当前的页码
			total : 0, // 总共的记录数
			showTotal : true,
			messageWhenHideTotal : '', // 当showTotal为false时, 默认显示的提示信息
			showRpSelect : true, // 是否显示每页多少条记录的选择框
			rp : 20, // 默认的每页显示的记录条数
			rpOptions : [10, 15, 20, 40, 50, 100], // 选择框里的选项
			
			initmsg : false, // 初始化提示信息
			
			pagestat : '共 {total} 条记录',
			procmsg : '正在处理，请稍等...',
			nomsg : '<em style="color:#A25A66;">没有记录！</em>',
			statusBar : false, // 分页栏右边的状态提示信息

			onChangePage : false, // 在分页之前触发的动作 function onChangePage(page, placeholder)
			onSuccess : false, // function onSuccess(placeholder, extra)
			onNoDataReturn : false, // function onNoDataReturn(placeholder, extra)
			preProcess : false, // 从后台得到数据后的处理操作 function preProcess(data, placeholder)
			onRpChange : false, // function onRpChange(rp, placeholder)
			onRowClick : false, // function onRowClick(tr, placeholder)
			// 行的双击事件, function onRowDblClick(tr, placeholder)
			onRowDblClick : false, 
			onTimeout : false, // 自定义的超时处理 function onTimeout(placeholder)
			onSubmit : false, // 提交到后台之前先执行的动作, function() {xxx; return true;}

			showDetail : false, // 显示详情回调函数 function showDetail(tr, placeholder)
			autoShowDetail : false, // 自动显示详情
			
			sortname : '', // 默认排序的字段
			sortorder : 'asc', // 默认排序的次序, ‘asc’ or ‘desc’
			
			newp : 0, // 要改变的页码
			
			hideToolbar : false, // 显示隐藏工具栏
			
			// private, don't set these values by yourself
			_loading : false, // 是否正在加载数据
			_pages: 1, // 总页数
			_overtime : true, // 是否已经超时
			_debug : false, // 是否测试性能
			_allButtonsHidden : false,
			_columnFields : [],
			_columns : '',
			_currentRowCount : 0
		}; // end of defaults
		
		var opts = $.extend({}, defaults, options);
		var dataIndexCache = {}; // 缓存dataIndex的值: {dataIndex:axis}
		var pageNumberCache = {}; // 缓存已加载的页码: {page:true}

		var placeHolderSign = $placeholder.attr('id') || $placeholder.attr('name');
		var globalContainerId = '_grid_global_' + placeHolderSign + '_';
		var rpSelectId = 'pager_rp_select_' + placeHolderSign;
		var totalPageInfoHolder = 'total_page_info_holder_' + placeHolderSign;
		var messageTipInfoHolder = 'message_tip_info_holder_' + placeHolderSign;
		var messageTipImageHolder = 'message_tip_image_holder_' + placeHolderSign;

		var buttonGroupIdPrefix = 'tinygrid-button-group-';
		
		var gdiv = buildGlobalContainer();
		var $gdiv = $(gdiv);
		var hdiv = document.createElement('div');	// 标题栏
		var tdiv = document.createElement('div');	// 工具栏
		var thdiv = document.createElement('div');	// 表格标题区
		var bdiv = document.createElement('div');	// 表格数据区
		var msgdiv = document.createElement('div');	// 处理提示区域
		var statdiv = document.createElement('div');	// 统计栏
		var $statdiv = $(statdiv);
		var pdiv = document.createElement('div');	// 分页栏

		var tdCount = 0;
		
		$placeholder.append(gdiv);
		
		if (opts.showTitle) {
			buildTitleContainer();
			$gdiv.append(hdiv);
		}
		
		$gdiv.append(tdiv);
		buildButtonsContainer();
		
		$gdiv.append(bdiv);
		buildTableContainer();

		buildMessageTipContainer();
		$gdiv.append(msgdiv);
		
		if (opts.usepager || opts.statusBar) {
			buildPagerContainer();
			$gdiv.append(pdiv);
		}
		
		$gdiv.append('<div style="clear:both;"></div>');
				
		/**
		 * 自适应高度
		 */
		if (opts.adaptiveHeight) {
			var gridOldPos = 0 ;
			
			$(document).ready(function () {
				gridOldPos =  $(gdiv).offset().top;	
				$(window).resize(function(event){
					var gridNewPos = $(gdiv).offset().top;	
					if (gridNewPos  != gridOldPos)	{
						var diffLager =gridNewPos  - gridOldPos ;
						//adjust the tinygrid height
						if(diffLager < $(bdiv).height()){
							var height = $(bdiv).height() - diffLager;
							tinygrid.setGirdHeight(height);
							gridOldPos = gridNewPos;
						}
					}
				});		
			});
		}	
		
		/**
		 * tinygrid object for exporting
		 */
		var tinygrid = {
			/**
			 * 加载最新的数据
			 * reflushCache 当reflushCache=true时重新读取参数
			 *              当reflushCache=false时读取缓存的参数
			 */
			populate : function(reflushCache) {
				if (opts._loading || !opts.url) {
					return false;
				}
				if (opts.onSubmit) {
					var gh = opts.onSubmit($placeholder);
					if (!gh) {
						return false;
					}
				}
				opts._loading = true;
				var procmsgTip=opts.procmsg;
				$('.pageStat', pdiv).html(procmsgTip);
				
				$('.reload', pdiv).addClass('loading');
				if (opts.showMessageTip) {
					tinygrid.showLoadingMessageTip();
				}
				if (!opts.newp) {
					opts.newp = 1;
				}
				if (opts.page > opts._pages) {
					opts.page = opts._pages;
				}
				var queryTotal = opts.showTotal;
				var param = opts.appendGridRequestParams ? [
					{name : 'page', value : opts.newp}, 
					{name : 'rp', value : opts.rp}, 
					{name : 'sortname', value : opts.sortname}, 
					{name : 'sortorder', value : opts.sortorder},
					{name : 'showTotal', value : queryTotal},
					{name : 'columns', value : opts._columns}
				] : [];
				if (opts.params) {
					for (var pi = 0, len = opts.params.length; pi < len; pi++) {
						param.push(opts.params[pi]);
					}
				}
				
				opts._startTime = new Date();
				
				if ((opts.total && !opts.cachePageData) || reflushCache) {
					$('tbody', bdiv).empty();
					pageNumberCache = {};
				}
				if (!reflushCache && opts.cachePageData) {
					$('tbody tr', bdiv).hide();
					if (pageNumberCache[opts.newp]) {
						if (opts.showMessageTip) {
							$('tbody', bdiv).show();
							$(msgdiv).hide();
						}
						opts._overtime = false;
						opts._loading = false;
						$('.reload', pdiv).removeClass('loading');
						opts.page = opts.newp;
						tinygrid.rebuildPager();
						$('tbody tr[page="' + opts.newp + '"]', bdiv).show();
						return false;
					}
				}
				$.ajax({
					type : opts.method,
					url : opts.url,
					data : param,
					dataType : opts.dataType,
					success : function(data) {
						opts._overtime = false;
						$('.reload', pdiv).removeClass('loading');
						opts._loading = false;
						tinygrid.addData(data);
					},
					error : function(xhr,status,e) {
						opts._overtime = false;
						$('.reload', pdiv).removeClass('loading');
						opts._loading = false;
						try {
							if (opts.onError) {
								opts.onError(xhr,status,e);
							} else {
								var errorCode = xhr.getResponseHeader("Error-TYPE");
							    var errorMsg = decodeURIComponent(xhr.getResponseHeader("Error-CRTS"));
							    if (errorMsg) {
									tinygrid.showErrorMessageTip(errorMsg);
							    }
								var errHeader = "Error-Information";
								var scriptText = xhr.getResponseHeader(errHeader);
								if(scriptText==null||scriptText==""){
									// tinygrid.showErrorMessageTip("数据读取错误！");
									return;
								}
								var errMsg = window["eval"]("(" + scriptText + ")");
								if (!errMsg) {
									return;
								}
								var message = decodeURI(decodeURI(errMsg.message));
								if (opts.onErrorType) {
									opts.onErrorType(errMsg.errorType, message, placeholder);
									return;
								} 
								if (errMsg.errorType == 'timeount') {
									window.alert(message);
									top.reloginFromMainJsp();
								} else {
									tinygrid.showErrorMessageTip(message);
								}
					   		}
						} catch (e) {tinygrid.showErrorMessageTip("数据读取错误！");}
					}
				});
				if (opts.useTimeout) {
					setTimeout(tinygrid.timeoutHandle, opts.timeout * 1000);
				}
			}, // end of populate
			
			addData : function (data) {
				if (opts.preProcess) {
					data = opts.preProcess(data, placeholder);
				}
				if (!data) {
					tinygrid.showErrorMessageTip();
					return false;
				}
				// dataType is json
				opts.total = data.total;
				if (opts.rp < 1) {
					opts.rp = 1;
				}
				opts._pages = Math.ceil(opts.total/opts.rp);
				if (opts.total == 0) {
					opts._pages = 1;
					opts.page = 1;
					tinygrid.rebuildPager();
					tinygrid.showNoDataMessageTip();
					if (opts.onNoDataReturn) {
						opts.onNoDataReturn(placeholder, data.extra && $.parseJSON(data.extra));
					}
					return false;
				}
				// 对于不设置记录总数 (total=-1) 的情况的处理 add by hwe 20100202
				if (opts.total < 0) {  // 不查总数
					var rowcnt = data.rows.length;
					
					opts._pages = 10000;
					var curPage = data.page;
														
					if (rowcnt < opts.rp) //当前行小于设定行数，说明已经到了最后一页
						opts._pages = curPage;
					if (rowcnt == 0) {   //没有数据 ，则认为该页是最后一页
						opts.page=curPage;
						opts._pages=curPage;
						tinygrid.rebuildPager();
						tinygrid.showNoDataMessageTip();
						if (opts.onNoDataReturn) {
							opts.onNoDataReturn(placeholder, data.extra && $.parseJSON(data.extra));
						}
						return false;					
					}
				}
                tinygrid.clearData();
				tinygrid.parseData(data);
				if (opts.showMessageTip) {
					$('tbody', bdiv).show();
					$(msgdiv).hide();
				}
				if (opts.onSuccess) {
					opts.onSuccess(placeholder, data.extra && $.parseJSON(data.extra));
				}
				if (opts.autoShowDetail) {
				    $("#" + globalContainerId + " tbody " + "."  + classNames.showDetailClass).click();
				}
				if (opts._debug) {
					window.alert('tinygrid耗时： ' + ((new Date() - opts._startTime) / 1000) + 's');
				}
			}, // end of addData
			
			showInitMessageTip : function() {
				tinygrid._showMessageTip(opts.initmsg, '', '');
			},
			
			showLoadingMessageTip : function() {	
				tinygrid._showMessageTip(opts.procmsg, 'loadingMessageTipClass', '');
			},
			
			_showMessageTip : function(msg, messageTipImageHolderClass, messageTipInfoHolderClass) {
				$('#' + messageTipImageHolder).removeClass().addClass(messageTipImageHolderClass);
				$('#' + messageTipInfoHolder).removeClass().addClass(messageTipInfoHolderClass).html(msg);
				$('tbody', bdiv).hide();
				if($(bdiv)[0].style.height) {
					$(msgdiv).height($(bdiv)[0].style.height).show();
				} else {
					$(msgdiv).height($(bdiv).height()).show();
				}
			},
			
			showErrorMessageTip : function(message) {
				$('.pageStat', pdiv).html('');
				tinygrid._showMessageTip('<em style="color:#A25A66;">' + (message || opts.errormsg) + '</em>', '', classNames.errorMessageTipClass);
			},
			
			showNoDataMessageTip : function() {
				$('.pageStat', pdiv).html(opts.nomsg);
				tinygrid._showMessageTip(opts.nomsg, '', classNames.errorMessageTipClass);
			},
			
			/**
			 * 清空表格的数据
			 */
			clearData : function() {
				$('tbody', bdiv).empty();
				opts._currentRowCount = 0;
				pageNumberCache = {};
			},
			
			parseData : function (data) {
				var rows = [];
				opts.page = +data.page;
				rows = data.rows;
				
				var rowDatas=[];
				var html = tinygrid.buildTableTrByRowsData(rows, opts._currentRowCount);
				opts._currentRowCount += rows.length;
				
				$('tbody', bdiv).append(html);  
				
				tinygrid.addTableTrEvent();
				tinygrid.rebuildPager();
				
				opts.cachePageData && (pageNumberCache[opts.page] = true);
			},
			
			buildTableTrByRowsData : function(rows, fromRowIndex) {
				var html = '';
				if (!rows || rows.length == 0) {
					return html;
				}
				var statistics = {}; // 统计结果
				// 累计值
				var accumulationCells = {}; // [dataIndex:[cellvalue]]
				$.each(rows, function(rowIndex, row) {
					var rowIndex = ++fromRowIndex;
					var trId = placeHolderSign + '_row_' + rowIndex;
					var trHtml = '<tr';
					trHtml += ' id = "' + trId + '"';
					trHtml += ' class="{rowClassPlaceHolder}"';
					trHtml += ' rowIndex="' + (rowIndex) + '"';
					trHtml += ' page="' + (opts.page || '1') + '"';
					trHtml += '>';
					var rowClass = '';
					
					// 缓存dataIndexs数据
					var dataIndexs = {};
					var cellRenderer2Models = {}; // {dataIndex:model};
					var cellClass2Models = {}; // {dataIndex:model};
					for (var cellIndex = 0, len = opts.colModel.length; cellIndex < len; cellIndex++) {
						var model = opts.colModel[cellIndex];
						if (!model) {
							return true;
						}
						var hide = model.hide;
						var lineBreak = model.lineBreak;
						var showTip = model.showTip;
						var innertext = model.field ? row[model.field] : null;
						innertext = innertext || '';
						dataIndexs[model.dataIndex] = innertext;
						if (model.rowClassRenderer && $.isFunction(model.rowClassRenderer)) {
							var classRenderer = model.rowClassRenderer(innertext, rowIndex);
							if (classRenderer) {
								rowClass = classRenderer;
							}
						}
						if (model.cellRenderer && $.isFunction(model.cellRenderer)) {
							innertext = model.cellRenderer(innertext, trId);
						}
						if (model.cellRenderer2 && $.isFunction(model.cellRenderer2)) {
							cellRenderer2Models[model.dataIndex] = model;
							innertext = '#{' + model.dataIndex + '}';
						}
						trHtml += '<td dataIndex="' + model.dataIndex + '" cellIndex="' + cellIndex + '"';
						trHtml += ' style="';
						if (hide) {
							trHtml += 'display : none;';
						}
						if (model.align && model.align != 'left') {
							trHtml += 'padding-left : 0px;';
						}
						if(model.tdCss){
							trHtml += model.tdCss;
						}
						trHtml += '">';
						
						if (model.cellClass2 && $.isFunction(model.cellClass2)) {
							cellClass2Models[model.dataIndex] = model;
						}
						trHtml += '<div'
						     + (model.details ? (' class="' + classNames.showDetailClass + ' ' + classNames.showDetailCloseClass + '"') : '')
							 + (!model.details && model.cellClass ? (' class="' + model.cellClass + '"') : '')
							 + (!model.details && model.cellClass2 ? (' class="' + ('$c{' + model.dataIndex + '}') + '"') : '')
							 + (showTip ? (' title=\"' 
							 + 					(model.cellRenderer2 
								 				? ('%{' + model.dataIndex + '}')
								 				: $.getTextFromHtml(innertext,true)) 
							 			   	+ '\"') 
							 			: '') + '>';
						if (!model.details && model.editable) {
							trHtml += '<input type="text" style="width:'+model.width+'" class="' + classNames.editableClass + ' '
									+ classNames.editableClass + '-' + (model.align ? model.align : 'right') + '"'
									+ ' value="' + innertext + '" />';
						} else if (!model.details) {
							trHtml += innertext;
						}
						trHtml += '</div>';
						trHtml += '</td>';
						if (model.countable) {
							if (!statistics[model.dataIndex]) {
								statistics[model.dataIndex] = {
									'value' : 0,
									'precision' : 0
								};
							}
							var statValue = $.getTextFromHtml(innertext);
							if (statValue && statValue.indexOf('.') > -1) {
								statistics[model.dataIndex].value += parseFloat(statValue) || 0;
								statistics[model.dataIndex].precision = model.precision || opts.defaultPrecision;
							} else {
								statistics[model.dataIndex].value += parseInt(statValue) || 0;
								statistics[model.dataIndex].precision = statistics[model.dataIndex].precision || 0;
							}
						}
					}
					trHtml += '</tr>';	
					for (var dataIndex in cellRenderer2Models) {
						var cellRenderer2 = cellRenderer2Models[dataIndex].cellRenderer2;
						var model = cellRenderer2Models[dataIndex];
						if (model.accumulation) {
							if (!accumulationCells[dataIndex]) {
								accumulationCells[dataIndex] = [];
							}
						}
						var cellValues = accumulationCells[dataIndex];
						var celltext = cellRenderer2(dataIndexs, trId, cellValues);
						trHtml = trHtml.replace("#{" + dataIndex + "}", celltext);
						trHtml = trHtml.replace("%{" + dataIndex + "}", $.getTextFromHtml(celltext));
						if (model.accumulation) {
							cellValues.push($.getTextFromHtml(celltext));
						}
					}
					for (var dataIndex in cellClass2Models) {
						var cellClass2 = cellClass2Models[dataIndex].cellClass2;
						var cellClass = cellClass2(dataIndexs, trId) || '';
						console.log(dataIndex + ' -> ' + cellClass);
						trHtml = trHtml.replace("$c{" + dataIndex + "}", cellClass);
					}
					html += trHtml.replace("{rowClassPlaceHolder}", rowClass);
				});
				return html;
			},
			
			addTableTrEvent : function () {
				var $trs = $('tbody tr', bdiv);
				var $checkboxs = $('td:first-child :checkbox', $trs).not(':disabled');
				// 勾选checkbox或radio
				$trs.click(function(e) {			
					var inputTag = $(e.target).prop('tagName').toUpperCase();	
					var $checkbox = $('td:first-child :checkbox', $(this)).not(':disabled');
					var $radio = $('td:first-child :radio', $(this)).not(':disabled');
					if (inputTag == 'A') { // 超链接
						e.stopPropagation(); // 不进行冒泡
						return;
					}
					if ($checkbox.length > 0 && opts.rowClickToDoTick && inputTag != 'INPUT') { // 通过tr打勾
						if (opts.rowClickJustCheckOne) {
							$checkboxs.prop('checked', false);
							$trs.removeClass(classNames.selectedClass).attr("selected",false);
						}
						if ($checkbox.prop('checked')) {
							$checkbox.prop('checked', false);
							$(this).removeClass(classNames.selectedClass).attr("selected",false);
						} else {
							$checkbox.prop('checked', true);
							$(this).addClass(classNames.selectedClass).attr("selected",true);
						}
					}
					if ($radio.length > 0 && opts.rowClickToDoTick) { // 通过tr打勾/通过radio打勾
						$radio.prop('checked', true);
						$('tbody tr', bdiv).removeClass(classNames.selectedClass).attr("selected", false);
						$(this).addClass(classNames.selectedClass).attr("selected", true);
					}
					if ($checkbox.length > 0 && inputTag == 'INPUT') { // 通过checkbox打勾
						if ($checkbox.prop('checked')) {
							$(this).addClass(classNames.selectedClass).attr("selected",true);
						} else {
							$(this).removeClass(classNames.selectedClass).attr("selected",false);
						}
					}
//					if (($checkbox.length + $radio.length) == 0) { // 不存在checkbox/radio情况下
//						$(this).toggleClass(classNames.selectedClass);
//						$(this).add("selected",$(this).hasClass(classNames.selectedClass));
//					}
				});
				// 单击事件
				if (opts.onRowClick) {
					$trs.click(function(e) {
						opts.onRowClick(this, placeholder);
					});
				}
				// 双击事件
				if (opts.onRowDblClick) {
					$trs.dblclick(function(e) {
						opts.onRowDblClick(this, placeholder);
					});
				}
			},
			
			rebuildTableHeader : function() {
			  	$('table', thdiv).empty();
				buildTableHeader();
			},
			
			rebuildPager : function () { //rebuild pager based on new properties
				$('.pcontrol input', pdiv).val(opts.page);	
				var stat = opts.pagestat.replace(/{total}/, opts.total);
				if (opts.showTotal) {
					$('#' + totalPageInfoHolder).html(opts._pages);
					$('.pageStat', pdiv).html(stat);
				} else {
					var message=opts.messageWhenHideTotal;
					
					$('.pageStat', pdiv).html(message);
				}
			},
			
			timeoutHandle : function() {
				if (opts._overtime) {
					if (opts.onTimeout) {
						opts.onTimeout(placeholder);
					} else {
						window.alert('超时！超时设置为：' + opts.timeout + 's');
					}
				}
			},
			
			/**
			 * 取得当前的总记录数
			 */
			getTotal : function() {
				return +opts.total;
			},
			
			/**
			 * 得到该页的所有行
			 */
			getAllRows : function() {
				return $('tbody tr', bdiv);
			},
			
			/**
			 * 得到被选中的行 <br>
			 * 若有checkbox/radio则根据打勾进行选择<br>
			 * 否则，根据selectedClass的样式定位被选中的行
			 * 
			 * @param {Boolean=} leaveDisabledAlone 默认false
			 */
			getSelectedRows : function(/*optional*/leaveDisabledAlone) {
				var checkboxs = $('td:first-child :checkbox,td:first-child :radio', bdiv);
				if (!leaveDisabledAlone) {
					checkboxs = checkboxs.not(':disabled');
				}
				if (checkboxs.length != 0) {
					return $('td:first-child :checkbox:checked,td:first-child :radio:checked', bdiv).parent().parent().parent('tr');
				}
				return $('.' + classNames.selectedClass, bdiv);
			},
			
			/**
			 * 得到被选中的行中的某一些单元格的值
			 */
			getSelectedCellValues : function (/*dataIndexs*/) {
				var values = [];
				var dataIndexs = arguments;
				if (arguments.length == 0) {
					return values;
				}
				if ($.isArray(arguments[0])) {
					dataIndexs = arguments[0];
				}
				return tinygrid.getCellValuesInSpecifiedRows(tinygrid.getSelectedRows(), dataIndexs);	
			},
			
			/**
			 * 得到指定的行中的某一些单元格的值
			 * 
			 * @param {Array}
			 *            rows 指定的行
			 * @param {Array}
			 *            dataIndexs 指定的单元格
			 * @return {Array} 指定行中的单元格值对象[{dataIndex:value}]
			 */
			getCellValuesInSpecifiedRows : function(rows, dataIndexs) {
				var undefinedValue;
				var values = [];
				$.each(rows, function(i, row) {
					var valueObj = {};
					valueObj['grid_row_index'] = $(row).attr('rowIndex');
					for (var index = 0, len = dataIndexs.length; index < len; index++) {
						var dataIndex = dataIndexs[index];
						if (dataIndex in dataIndexCache) {
							var text = null;
							var $input = $('td[dataIndex="' + dataIndex + '"] :input', row);
							if ($input.length > 0) {
								text = $input.val();
							} else {
								text = $('td[dataIndex="' + dataIndex + '"]', row).text();
							}
							valueObj[dataIndex] = text;
						} else {
							valueObj[dataIndex] = undefinedValue;							
						}
					}
					values.push(valueObj);
				});
				return values;	
			},
			
			/**
			 * 设置指定的行中的某一些单元格的值
			 * 
			 * @param {Array}
			 *            rows 指定的行
			 * @param {Object|Array} 指定行中的单元格值对象{dataIndex:value}|[{dataIndex:value}]
			 */
			setCellValuesInSpecifiedRows : function(rows, dataIndexValues) {
				var dataIndexValuesArray = [];
				if ($.isArray(dataIndexValues)) {
					dataIndexValuesArray = dataIndexValues;
				} else {
					dataIndexValuesArray.push(dataIndexValues);
				}
				var defaultDataIndexValues = dataIndexValuesArray[0];
				$.each(rows, function(i, row) {
					var _dataIndexValues = dataIndexValuesArray[i] || defaultDataIndexValues;
					for (var dataIndex in _dataIndexValues) {
						var value = _dataIndexValues[dataIndex];
						if (dataIndex in dataIndexCache) {
							var $td = $('td[dataIndex="' + dataIndex + '"] div', row);
							var $input = $('td[dataIndex="' + dataIndex + '"] :input', row);
							if ($input.length == 0) {
								$td.text(value);
							} else {
								$input.val(value);
							}
						}
					}
				});
			},
			
			/**
			 * 得到所有行中的某一些单元格的值
			 * 
			 * @param {Array}
			 *            dataIndexs 指定的单元格
			 * @return {Array} 指定行中的单元格值对象[{dataIndex:value}]
			 */
			getCellValuesInAllRows : function(dataIndexs) {
				return tinygrid.getCellValuesInSpecifiedRows(tinygrid.getAllRows(), dataIndexs);
			},
			
			hideColumns : function(/*dataIndexs*/) {
				var dataIndexs = arguments;
				if (arguments.length == 0) {
					return false;
				}
				if ($.isArray(arguments[0])) {
					dataIndexs = arguments[0];
				}
				for (var index = 0, len = dataIndexs.length; index < len; index++) {
					$('table tr th[dataIndex="' + dataIndexs[index] + '"]', gdiv).hide();
					$('table tr td[dataIndex="' + dataIndexs[index] + '"]', gdiv).hide();
				}
			},
			
			showColumns : function(/*dataIndexs*/) {
				var dataIndexs = arguments;
				if (arguments.length == 0) {
					return false;
				}
				if ($.isArray(arguments[0])) {
					dataIndexs = arguments[0];
				}
				for (var index = 0, len = dataIndexs.length; index < len; index++) {
					$('table tr th[dataIndex="' + dataIndexs[index] + '"]', gdiv).show();
					$('table tr td[dataIndex="' + dataIndexs[index] + '"]', gdiv).show();
				}
			},
			
			changeHeader : function(dataIndex, header) {
				$('table tr th[dataIndex="' + dataIndex + '"] div', gdiv).html(header);
			},
			
			setGirdHeight :function(height){
				$(bdiv).height(height);
				$(msgdiv).height(height);
			},
			
			changeSort : function(dataIndex, sortorder) {
				var $th = $('table tr th[dataIndex="' + dataIndex + '"]', gdiv);
				changeSort($th[0], false, sortorder);
			}			
		}; // end of tinygrid object
		
		//make tinygrid functions accessible
		placeholder.opts = opts;
		placeholder.tinygrid = tinygrid;
		if (opts.autoload) {
			tinygrid.populate();
		} else if (opts.initmsg) {
			tinygrid.showInitMessageTip();	
		}
		
		
		//-------------------------------------------------------
		//------------ some private functions -------------------------
		/**
		 * 创建全局tinygrid的div容器
		 */
		function buildGlobalContainer() {
			var gdiv = document.createElement('div');
			gdiv.id = globalContainerId;
			gdiv.className = classNames.globalContainerClass;
			return gdiv;
		}
		
		function buildTitleContainer() {
			hdiv.className = classNames.titleContainerClass;
			var $hdiv = $(hdiv);
			var title = '<div class="' + classNames.titleClass + '"><span>'
					+ (opts.title ? opts.title : '') + '</span></div>';
			
			$hdiv.append(title);
		}
		
		/**
		 * 创建按钮区域
		 */
		function buildButtonsContainer() {
			if (isNoneButton()) {
				return false;
			}
			
			var leftAndRightButtons = getLeftAndRightButtons();
			var leftButtons = leftAndRightButtons[0], rightButtons = leftAndRightButtons[1];

			tdiv.className = classNames.buttonContainerClass;
			var bdivLeft = document.createElement('div');
			bdivLeft.className = classNames.innerContainer;
			var $bdivLeft = $(bdivLeft);
			
			var bdivRight = document.createElement('div');
			bdivRight.className = classNames.innerContainer4Right;
			var $bdivRight = $(bdivRight);
			
			var leftAllBtnHidden = buildButtonsInnerContainer(leftButtons, $bdivLeft);
			var rightAllBtnHidden = buildButtonsInnerContainer(rightButtons, $bdivRight);

			opts._allButtonsHidden = leftAllBtnHidden && rightAllBtnHidden
					&& opts.customizeButtonHtml == '';
			
			var $tdiv = $(tdiv);
			if (opts.hideToolbar || opts._allButtonsHidden) {
				$tdiv.hide();
			}
			$tdiv.append(bdivLeft);
			$tdiv.append(bdivRight);
			$tdiv.append(opts.customizeButtonHtml);
			$tdiv.append('<div style="clear : both;"></div>');
		} // end of buildButtonsContainer
		
		function getLeftAndRightButtons() {
			if (!opts.buttons) {
				return [[], []];
			}
			var leftButtons = [], rightButtons = [];
			if ($.isArray(opts.buttons)) {
				leftButtons = opts.buttons;
			} else {
				leftButtons = opts.buttons['left'] || [];
				rightButtons = opts.buttons['right'] || [];
			}
			return [leftButtons, rightButtons];
		}
		function isNoneButton() {
			var leftAndRightButtons = getLeftAndRightButtons();
			var leftButtons = leftAndRightButtons[0], rightButtons = leftAndRightButtons[1];
			return leftButtons.length + rightButtons.length == 0 && opts.customizeButtonHtml == '';
		}
		
		// 返回: 是否所有按钮隐藏?
		function buildButtonsInnerContainer(buttons, $container) {
			if (!buttons || buttons.length == 0) {
				return true;
			}
			var allButtonHidden = true;
			for (var i = 0, len = buttons.length; i < len; i++) {
				var btn = buttons[i];
				if (!btn) {
					continue;
				}
				if (allButtonHidden && !btn.hide) {
					allButtonHidden = false;
				}
				var item = createNormalButton(btn, false);
				$container.append(item);
			} // end of for
			return allButtonHidden;
		}
		
		/**
		 * 创建按钮DIV DOM
		 * 
		 * @param {Object}
		 *            btn 按钮设置信息
		 * @param {Boolean}
		 *            isGroupItem 是否是按钮群里的按钮
		 * @return {DOM} 按钮DIV DOM
		 */
		function createNormalButton(btn, isGroupItem) {
			var undefined;
			if (btn.html !== undefined) {
				return createNormalHtml4Toolbar(btn);
			}
			var btnDiv = document.createElement('div');
			if (isGroupItem) {
				btnDiv.className = classNames.buttonGroupItemClass;
			} else {
				btnDiv.className = classNames.buttonItemClass;
			}
			var $btnDiv = $(btnDiv);
			$btnDiv.attr('buttonItem', 'true');
			if (btn.id) {
				btnDiv.id = btn.id;
			}
			if (btn.tip) {
				btnDiv.title = btn.tip;
			}
			btnDiv.innerHTML = btn.name;
			if (btn.bclass) {
				$btnDiv.addClass(btn.bclass);
			}
			if (btn.hide) {
				$btnDiv.css('display', 'none');
			}
			btnDiv.onpress = btn.onpress;
			btnDiv.name = btn.name;
			if (!btn.disabled && btn.onpress) {
				$btnDiv.click(function () {
					if ($.isFunction(this.onpress)) {
						this.onpress(this.name, placeholder, this.id);
					} else { 
						eval(this.onpress + '(this.name, placeholder, this.id);');
					}
				});
			} 
			return btnDiv;
		}
		
		function createNormalHtml4Toolbar(btn) {
			var normalDiv = document.createElement('div');
			var $normalDiv = $(normalDiv);
			if (btn.id) {
				normalDiv.id = btn.id;
			}
			if (btn.tip) {
				normalDiv.title = btn.tip;
			}
			$normalDiv.addClass(classNames.toolbarItemClass);
			if (btn.bclass) {
				$normalDiv.addClass(btn.bclass);
			}
			normalDiv.innerHTML = btn.html;
			return normalDiv;
		}
		
		/**
		 * 创建表格数据区
		 */
		function buildTableContainer() {
			bdiv.className = classNames.tableContainerClass;
			var table = document.createElement('table');
			buildTableHeader(table);
			$(table).addClass(classNames.tableClass).append(document.createElement('tbody'));
			$(bdiv).append(table);
			buildSortClickEvent();
			buildShowDetailEvent();
		}
		
		function buildTableHeader(table) {
			if (opts.colModel) {
				var thead = document.createElement('thead');
				var tr = document.createElement('tr');
				
				var $tr = $(tr);
				opts._columnFields = [];
                for (var i = 0, len = opts.colModel.length; i < len; i++) {
                    tdCount++;
                    var cm = opts.colModel[i];
                    opts._columnFields.push(cm.field);
		            dataIndexCache[cm.dataIndex] = i;
		            if(opts.sortname){
		            	$tr.append(createTableHeadTh(cm, i,opts.sortname.contains(cm.field)));
		            }else{
		            	$tr.append(createTableHeadTh(cm, i,null));
		            }
                } // end of for
				$(thead).append(tr);
				$(table).prepend(thead);
				opts._columns = opts._columnFields.join(',');
				
			} // end if opts.colmodel
		}
		function createTableHeadTh(cm, axis, sort) {
			var th = document.createElement('th');
			var $th = $(th);
			$th.attr('dataIndex', cm.dataIndex || '');
			$th.attr('axis', axis);
			var div = '<div style="';
			cm.align = cm.align ? cm.align : 'left';
			th.align = cm.align;
			if (th.align != 'left') {
				$th.css('padding-left', '0px');
			}
			div += 'text-align: ' + cm.align + ';';
			div += 'overflow:hidden; text-overflow:ellipsis; white-space:nowrap;'; // 过长的文字隐藏
			div += '"'; // end of div style
			div += cm.cellClass ? (' class="' + cm.cellClass + '"') : '';
			
			cm.hide = cm.hide || false;
			
			$th.attr('hide', !!cm.hide);
			if (cm.hide) {
				th.style.display = 'none';
			}
			div += '> '; // end of div start tag
			if(sort){
				div += '<a href = "javascript:void(0)" name="sort" data-fild="'+cm.field+'" data-sortrul="">';
				div += cm.header + '</div>';
				div += '</a>'
			}else{
				div += cm.header + '</div>';
			}
			$th.html(div);
			return th;
		}

		/**
		 * 创建排序点击事件
		 */
		function buildSortClickEvent(){
			$("th a").on("click",function(){
				var sortType = $(this).attr('data-sortrul');
				if(!sortType || sortType == '' || sortType == 'desc' ){
					opts.sortname = $(this).attr('data-fild');
					opts.sortorder = 'asc';	
					$(this).attr('data-sortrul','asc');
				}else{
					opts.sortname = $(this).attr('data-fild');
					opts.sortorder = 'desc';
					$(this).attr('data-sortrul','desc');
				}
				tinygrid.populate();
			})
		}

		function buildShowDetailEvent() {
            $("#" + globalContainerId + " tbody").on("click", "."  + classNames.showDetailClass, function() {
                var $this = $(this), isOpen = $this.hasClass(classNames.showDetailOpenClass), $tr = $this.parents('tr'),
                    trId = $tr.attr('id'), detailTrId = trId + '_detail';
                if (isOpen) {
                    $('#' + detailTrId).remove();
                    $this.removeClass(classNames.showDetailOpenClass).addClass(classNames.showDetailCloseClass);
                } else {
                    var detailHtml = options.showDetail && options.showDetail($tr[0], placeholder);
                    if (detailHtml) {
                        var detailTrHtml = '<tr id="' + detailTrId + '">' +
                                '<td colspan="' + tdCount + '" class="' + classNames.detailTdClass + '">' + detailHtml +
                                '</td></tr>';
                        $tr.after(detailTrHtml);
                    }
                    $this.removeClass(classNames.showDetailCloseClass).addClass(classNames.showDetailOpenClass);
                }
                return false;
            });
		}

		/**
		 * 创建信息提示区
		 */
		function buildMessageTipContainer() {
			msgdiv.className = classNames.messageTipContainerClass;
			msgdiv.style.display = 'none';
			var height = opts.minheight || 90;
			var blankDiv = document.createElement('div');
			blankDiv.innerHTML = '&nbsp;<br/>&nbsp;';
			blankDiv.style.paddingTop = (height / 3 ) + 'px';
			var div = document.createElement('div');
			div.className = 'messageGroupContainerClass';
			var imageDiv = document.createElement('div');
			imageDiv.id = messageTipImageHolder;
			$(imageDiv).css('float', 'left');
			var infoDiv = document.createElement('div');
			infoDiv.id = messageTipInfoHolder;
			$(infoDiv).css('float', 'left');
			$(div).append(imageDiv).append(infoDiv);
			$(msgdiv).empty().append(blankDiv).append(div).append(blankDiv);
		}
		
		/**
		 * 创建分页栏
		 */
		function buildPagerContainer() {
			pdiv.className = classNames.pagerContainerClass;
			var html = '';
			if (opts.usepager) {
				html += opts.showRpSelect ? createRecordsPerPageSelect() : '';
				html += '<div class="pbutton first" title="第一页"></div>';
				html += '<div class="pbutton prev" title="上一页"><span></span></div>';
				html += '<div class="group pcontrol">第 <input type="text" size="4" value="1"  title="当前页码"/> 页';
				html += opts.showTotal ? ('，共 ' + '<span id="' + totalPageInfoHolder + '">1</span> 页') : '';
				html += '</div>';
				html += '<div class="pbutton next" title="下一页"></div>';
				html +=  opts.showTotal ? ('<div class="pbutton last" title="最后一页"></div>'):'';
				html += '<div class="pbutton reload" title="重新加载"></div>';
				html += '<div class="group pageStat" title="记录信息"></div>';	
			}
			if (opts.statusBar) {
				html += '<div class="status-bar">' + (opts.statusBar || '') + '</div>';
			}
			pdiv.innerHTML = html;
			if (opts.usepager) {
				addPagerClickEvent(opts);
			}
		}
		function addPagerClickEvent() {
			$('.first', pdiv).click(function() {
				changePage('first');
			});	
			$('.prev', pdiv).click(function() {
				changePage('prev');
			});	
			$('.next', pdiv).click(function() {
				changePage('next');
			});	
			$('.last', pdiv).click(function() {
				changePage('last');
			});	
			$('.reload', pdiv).click(function() {
				changePage('reload');
			});	
			$('.pcontrol input', pdiv).keydown(function(e) {
				if(e.keyCode == 13) {
					changePage('input');
				}
			});
			$('select', pdiv).change(function () {
				if (opts.onRpChange && !opts.onRpChange(+this.value, placeholder)) {
					return;
				}
				opts.newp = 1;
				opts.rp = +this.value;
				tinygrid.populate(true);
			});
		}
		
		function createRecordsPerPageSelect() {
			var html = '<div class="group">';
			html += '<select id="' + rpSelectId + '" title="每页显示的记录数">';
			$.each(opts.rpOptions, function(i, rp) {
				html += '<option value="' + rp + '"';
				html += (rp == opts.rp) ? ' selected="selected"' : '';
				html += '>' + rp + '</option>';
			});
			html += '</select></div>';
			return html;
		}
		
		function changePage(mode) {
			if (opts._loading) {
				return true;
			}
			if ((mode == 'first' || mode == 'prev') && opts.page == 1) {
				return true; 
			}
			if ((mode == 'last' || mode == 'next') && opts.page == opts._pages) {
				return true; 
			}
			switch (mode) {
				case 'first' :
					opts.newp = 1;
					break;
				case 'prev' :
					if (opts.page > 1) {
						opts.newp = parseInt(opts.page) - 1;
					}
					break;
				case 'next' :
					if (opts.page < opts._pages) {
						opts.newp = parseInt(opts.page) + 1;
					}
					break;
				case 'last' :
					opts.newp = opts._pages;
					break;
				case 'input' :
					var nv = parseInt($('.pcontrol input', pdiv).val());
					if (isNaN(nv)) {
						nv = 1;
					}
					if (nv < 1) {
						nv = 1;
					} else if (nv > opts._pages) {
						nv = opts._pages;
					}
					$('.pcontrol input', this.pDiv).val(nv);
					opts.newp = nv;
					break;
			}
			if (opts.onChangePage) {
				opts.onChangePage(opts.newp, placeholder);
			}
			tinygrid.populate();
		}
	}; // end of addTinygrid
	
	// ----------------------------------------------------------------------------
	//-----------------------------扩展的插件方法----------------------------
	//----------------------------------------------------------------------------	
	var docloaded = false; // 确保HTML加载完毕

	$(document).ready(function() {docloaded = true;} );
	
	/**
	 * 该插件的主方法
	 */
	$.fn.tinygrid = function(options) {
		return this.each(function() {
			if (!docloaded) {
				$(this).hide();
				$(document).ready(function() {
					$.addTinygrid(this, options);
				});
			} else {
				$.addTinygrid(this, options);
			}
		});
	};
	/**
	 * function to reload tinygrid
	 */
	$.fn.gridReload = function(options) {
		return this.each( function() {
			if (this.tinygrid && this.opts.url) {
                //
				this.tinygrid.populate();
			}
		});
	}; //end gridReload

	/**
	 * function to update general options
	 */
	$.fn.gridOptions = function(options) { 
		return this.each( function() {
			if (this.tinygrid) {
				$.extend(this.opts, options);
			}
		});
	}; //end gridOptions
    $.fn.clearRecords = function(){
        return this.each( function() {
            if (this.tinygrid ) {
              this.tinygrid.clearData();
            }
        });
    };
    $.fn.addRecords = function(record,isClear) {
        var data = {
            "rows": record
        };

        return this.each( function() {
            if (this.tinygrid ) {
                if(isClear){
                    this.tinygrid.clearData();
                }
                this.tinygrid.addData(data);
            }
        });
    };//end addRecords
    })(jQuery);
