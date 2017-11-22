//老河口
"use strict";
$
	.widget(
		"ui.selectWidget", {
			options: {
				change: function(e) {
					return e
				},
				effect: "slide",
				keyControl: true,
				speed: 200,
				scrollHeight: 250
			},
			_create: function() {
				this._selectFunctional()
			},
			_selectFunctional: function() {
				function v(t) {
					$("li.active", s).removeClass("active");
					$("li", s).eq(t).addClass("active");
					$("option[selected]", e).removeAttr("selected");
					u.eq(t).prop("selected", true);
					e.val(u.eq(t).val());
					n.text(u.eq(t).text());
					a = t;
					if(p) {
						e.change(u.eq(t).val());
						p(e.val())
					}
					return t
				}

				function m() {
					var e = 0,
						t = $(".select-items", s);
					for(var n = 0, r = t.length; n < r; n++) {
						if($(t[n]).hasClass("active")) {
							break
						}
						e += $(t[n]).outerHeight()
					}
					s.stop().animate({
						scrollTop: e
					}, l)
				}
				if(this.element.is(":hidden")) {
					return false
				}
				var e = this.element,
					t = $("<div>").addClass(
						"select-main"),
					n = $("<div>").addClass(
						"select-set"),
					r = $("<div>").addClass(
						"select-arrow"),
					i = $("<div>").addClass(
						"select-block"),
					s = $("<ul>").addClass(
						"select-list"),
					o = e.find("option[selected]").length,
					u = $(
						"option", e),
					a = 0,
					f = $("input[type=reset]",
						e.parents("form")),
					l = this.options.speed,
					c = this.options.keyControl,
					h = this.options.effect,
					p = this.options.change,
					d = this.options.scrollHeight;
				t.append(r);
				t.append(n);
				i.append(s);
				t.append(i);
				e.after(t);
				e.hide();
				i.hide();
				u.each(function(e, t) {
					s.append($("<li>").addClass("select-items").text(
						$(t).text()))
				});
//				$(".select-items").click(function () {
//					alert($(this).text())
//				})
				
				f.click(function() {
					$(u, $(this).parents("form")).each(function(e, t) {
						if($(t).val() == "" && !$(t).is(":selected")) {
							$(".select-items:first", s).click();
							return false
						}
					})
				});
				if(e.attr("disabled")) {
					t.addClass("disabled")
				}
				if(!o) {
					e.val(u.first().val());
					//							$("li:first", s).addClass("active");
					n.text(u.first().text())
				} else {
					e.val(e.find("option[selected]").val());
					$("li", s).eq(e.find("option[selected]").index())
						.addClass("active");
					n.text(e.find("option[selected]").text())
				}
				a = $("option[selected]", e).index();
				$("li", s).click(function() {
					var e = $(this).index();
					a = e;
					v(e)
				});
				if(d) {
					if(i.height() > d) {
						s.css("height", d).css("overflow", "auto")
					}
				}
				r.click(function() {
					n.click()
				});
				n
					.click(function() {
						var e = $(this);
						if(e.parent(t).hasClass("disabled")) {
							return false
						}
						if(!e.prev().hasClass("reverse")) {
							$(".select-arrow").removeClass(
								"reverse");
							e.prev().addClass("reverse");
							e.parent().addClass("z-index")
						} else {
							e.parent().removeClass("z-index");
							e.prev().removeClass("reverse")
						}
						if(i.is(":hidden")) {
							if($(".select-block").is(":visible")) {
								if(h == "slide") {
									$(".select-block").slideUp(
										l / 2);
									$(".select-main").removeClass("z-index")
								} else if(h == "fade") {
									$(".select-block").fadeOut(l / 2);
									$(".select-main").removeClass("z-index")
								}
								$(document).unbind("keydown")
							}
							$(document)
								.bind(
									"click",
									function(e) {
										if(!$(e.target)
											.is(
												".select-set") &&
											!$(
												e.target)
											.is(
												".select-arrow")) {
											if($(
													".select-block")
												.is(
													":visible")) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow")
														.removeClass(
															"reverse")
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow")
														.removeClass(
															"reverse")
												}
												$(document)
													.unbind(
														"click keydown")
											}
										}
									});
							if(h == "slide") {
								i.slideDown(l);
								e.parent().addClass("z-index");
								m()
							} else if(h == "fade") {
								i.fadeIn(l);
								e.parent().addClass("z-index");
								m()
							}
							if(c) {
								$(document)
									.bind(
										"keydown",
										function(e) {
											var t = e.keyCode;
											if(t == 40) {
												if(a < u.length - 1) {
													a = a == -1 ? 0 :
														a;
													v(a += 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 38) {
												if(a > 0) {
													v(a -= 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 13) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main")
																	.removeClass(
																		"z-index")
															})
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main")
																	.removeClass(
																		"z-index")
															})
												}
												$(
														".select-arrow")
													.removeClass(
														"reverse");
												$(document)
													.unbind(
														"keydown");
												e
													.preventDefault()
											}
										})
							}
						} else {
							if(h == "slide") {
								i.slideUp(l / 2, function() {
									$(".select-main").removeClass(
										"z-index")
								});
								$(".select-arrow").removeClass(
									"reverse")
							} else if(h == "fade") {
								i.fadeOut(l / 2, function() {
									$(".select-main").removeClass(
										"z-index")
								});
								$(".select-arrow").removeClass(
									"reverse")
							}
							$(document).unbind("keydown")
						}
					})
			}
		}
		);
	
//谷城县	
"use strict";
$
	.widget(
		"ui.selectWidget1", {
			options: {
				change: function(e) {
					return e
				},
				effect: "slide",
				keyControl: true,
				speed: 200,
				scrollHeight: 250
			},
			_create: function() {
				this._selectFunctional()
			},
			_selectFunctional: function() {
				function v(t) {
					$("li.active", s).removeClass("active");
					$("li", s).eq(t).addClass("active");
					$("option[selected]", e).removeAttr("selected");
					u.eq(t).prop("selected", true);
					e.val(u.eq(t).val());
					n.text(u.eq(t).text());
					a = t;
					if(p) {
						e.change(u.eq(t).val());
						p(e.val())
					}
					return t
				}

				function m() {
					var e = 0,
						t = $(".select-items1", s);
					for(var n = 0, r = t.length; n < r; n++) {
						if($(t[n]).hasClass("active")) {
							break
						}
						e += $(t[n]).outerHeight()
					}
					s.stop().animate({
						scrollTop: e
					}, l)
				}
				if(this.element.is(":hidden")) {
					return false
				}
				var e = this.element,
					t = $("<div>").addClass(
						"select-main1"),
					n = $("<div>").addClass(
						"select-set1"),
					r = $("<div>").addClass(
						"select-arrow1"),
					i = $("<div>").addClass(
						"select-block1"),
					s = $("<ul>").addClass(
						"select-list1"),
					o = e.find("option[selected]").length,
					u = $(
						"option", e),
					a = 0,
					f = $("input[type=reset]",
						e.parents("form")),
					l = this.options.speed,
					c = this.options.keyControl,
					h = this.options.effect,
					p = this.options.change,
					d = this.options.scrollHeight;
				t.append(r);
				t.append(n);
				i.append(s);
				t.append(i);
				e.after(t);
				e.hide();
				i.hide();
				u.each(function(e, t) {
					s.append($("<li>").addClass("select-items1").text(
						$(t).text()))
				});
//				$(".select-items").click(function () {
//					alert($(this).text())
//				})
				
				f.click(function() {
					$(u, $(this).parents("form")).each(function(e, t) {
						if($(t).val() == "" && !$(t).is(":selected")) {
							$(".select-items1:first", s).click();
							return false
						}
					})
				});
				if(e.attr("disabled")) {
					t.addClass("disabled")
				}
				if(!o) {
					e.val(u.first().val());
					//							$("li:first", s).addClass("active");
					n.text(u.first().text())
				} else {
					e.val(e.find("option[selected]").val());
					$("li", s).eq(e.find("option[selected]").index())
						.addClass("active");
					n.text(e.find("option[selected]").text())
				}
				a = $("option[selected]", e).index();
				$("li", s).click(function() {
					var e = $(this).index();
					a = e;
					v(e)
				});
				if(d) {
					if(i.height() > d) {
						s.css("height", d).css("overflow", "auto")
					}
				}
				r.click(function() {
					n.click()
				});
				n
					.click(function() {
						var e = $(this);
						if(e.parent(t).hasClass("disabled")) {
							return false
						}
						if(!e.prev().hasClass("reverse")) {
							$(".select-arrow1").removeClass(
								"reverse");
							e.prev().addClass("reverse");
							e.parent().addClass("z-index")
						} else {
							e.parent().removeClass("z-index");
							e.prev().removeClass("reverse")
						}
						if(i.is(":hidden")) {
							if($(".select-block1").is(":visible")) {
								if(h == "slide") {
									$(".select-block1").slideUp(
										l / 2);
									$(".select-main1").removeClass("z-index")
								} else if(h == "fade") {
									$(".select-block1").fadeOut(l / 2);
									$(".select-main1").removeClass("z-index")
								}
								$(document).unbind("keydown")
							}
							$(document)
								.bind(
									"click",
									function(e) {
										if(!$(e.target)
											.is(
												".select-set1") &&
											!$(
												e.target)
											.is(
												".select-arrow1")) {
											if($(
													".select-block1")
												.is(
													":visible")) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main1")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow1")
														.removeClass(
															"reverse")
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main1")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow1")
														.removeClass(
															"reverse")
												}
												$(document)
													.unbind(
														"click keydown")
											}
										}
									});
							if(h == "slide") {
								i.slideDown(l);
								e.parent().addClass("z-index");
								m()
							} else if(h == "fade") {
								i.fadeIn(l);
								e.parent().addClass("z-index");
								m()
							}
							if(c) {
								$(document)
									.bind(
										"keydown",
										function(e) {
											var t = e.keyCode;
											if(t == 40) {
												if(a < u.length - 1) {
													a = a == -1 ? 0 :
														a;
													v(a += 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 38) {
												if(a > 0) {
													v(a -= 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 13) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main1")
																	.removeClass(
																		"z-index")
															})
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main1")
																	.removeClass(
																		"z-index")
															})
												}
												$(
														".select-arrow1")
													.removeClass(
														"reverse");
												$(document)
													.unbind(
														"keydown");
												e
													.preventDefault()
											}
										})
							}
						} else {
							if(h == "slide") {
								i.slideUp(l / 2, function() {
									$(".select-main1").removeClass(
										"z-index")
								});
								$(".select-arrow1").removeClass(
									"reverse")
							} else if(h == "fade") {
								i.fadeOut(l / 2, function() {
									$(".select-main1").removeClass(
										"z-index")
								});
								$(".select-arrow1").removeClass(
									"reverse")
							}
							$(document).unbind("keydown")
						}
					})
			}
		}
		);
//保康县	
"use strict";
$
	.widget(
		"ui.selectWidget2", {
			options: {
				change: function(e) {
					return e
				},
				effect: "slide",
				keyControl: true,
				speed: 200,
				scrollHeight: 250
			},
			_create: function() {
				this._selectFunctional()
			},
			_selectFunctional: function() {
				function v(t) {
					$("li.active", s).removeClass("active");
					$("li", s).eq(t).addClass("active");
					$("option[selected]", e).removeAttr("selected");
					u.eq(t).prop("selected", true);
					e.val(u.eq(t).val());
					n.text(u.eq(t).text());
					a = t;
					if(p) {
						e.change(u.eq(t).val());
						p(e.val())
					}
					return t
				}

				function m() {
					var e = 0,
						t = $(".select-items2", s);
					for(var n = 0, r = t.length; n < r; n++) {
						if($(t[n]).hasClass("active")) {
							break
						}
						e += $(t[n]).outerHeight()
					}
					s.stop().animate({
						scrollTop: e
					}, l)
				}
				if(this.element.is(":hidden")) {
					return false
				}
				var e = this.element,
					t = $("<div>").addClass(
						"select-main2"),
					n = $("<div>").addClass(
						"select-set2"),
					r = $("<div>").addClass(
						"select-arrow2"),
					i = $("<div>").addClass(
						"select-block2"),
					s = $("<ul>").addClass(
						"select-list2"),
					o = e.find("option[selected]").length,
					u = $(
						"option", e),
					a = 0,
					f = $("input[type=reset]",
						e.parents("form")),
					l = this.options.speed,
					c = this.options.keyControl,
					h = this.options.effect,
					p = this.options.change,
					d = this.options.scrollHeight;
				t.append(r);
				t.append(n);
				i.append(s);
				t.append(i);
				e.after(t);
				e.hide();
				i.hide();
				u.each(function(e, t) {
					s.append($("<li>").addClass("select-items2").text(
						$(t).text()))
				});
//				$(".select-items").click(function () {
//					alert($(this).text())
//				})
				
				f.click(function() {
					$(u, $(this).parents("form")).each(function(e, t) {
						if($(t).val() == "" && !$(t).is(":selected")) {
							$(".select-items2:first", s).click();
							return false
						}
					})
				});
				if(e.attr("disabled")) {
					t.addClass("disabled")
				}
				if(!o) {
					e.val(u.first().val());
					//							$("li:first", s).addClass("active");
					n.text(u.first().text())
				} else {
					e.val(e.find("option[selected]").val());
					$("li", s).eq(e.find("option[selected]").index())
						.addClass("active");
					n.text(e.find("option[selected]").text())
				}
				a = $("option[selected]", e).index();
				$("li", s).click(function() {
					var e = $(this).index();
					a = e;
					v(e)
				});
				if(d) {
					if(i.height() > d) {
						s.css("height", d).css("overflow", "auto")
					}
				}
				r.click(function() {
					n.click()
				});
				n
					.click(function() {
						var e = $(this);
						if(e.parent(t).hasClass("disabled")) {
							return false
						}
						if(!e.prev().hasClass("reverse")) {
							$(".select-arrow2").removeClass(
								"reverse");
							e.prev().addClass("reverse");
							e.parent().addClass("z-index")
						} else {
							e.parent().removeClass("z-index");
							e.prev().removeClass("reverse")
						}
						if(i.is(":hidden")) {
							if($(".select-block2").is(":visible")) {
								if(h == "slide") {
									$(".select-block2").slideUp(
										l / 2);
									$(".select-main2").removeClass("z-index")
								} else if(h == "fade") {
									$(".select-block2").fadeOut(l / 2);
									$(".select-main2").removeClass("z-index")
								}
								$(document).unbind("keydown")
							}
							$(document)
								.bind(
									"click",
									function(e) {
										if(!$(e.target)
											.is(
												".select-set2") &&
											!$(
												e.target)
											.is(
												".select-arrow2")) {
											if($(
													".select-block2")
												.is(
													":visible")) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main2")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow2")
														.removeClass(
															"reverse")
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main2")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow2")
														.removeClass(
															"reverse")
												}
												$(document)
													.unbind(
														"click keydown")
											}
										}
									});
							if(h == "slide") {
								i.slideDown(l);
								e.parent().addClass("z-index");
								m()
							} else if(h == "fade") {
								i.fadeIn(l);
								e.parent().addClass("z-index");
								m()
							}
							if(c) {
								$(document)
									.bind(
										"keydown",
										function(e) {
											var t = e.keyCode;
											if(t == 40) {
												if(a < u.length - 1) {
													a = a == -1 ? 0 :
														a;
													v(a += 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 38) {
												if(a > 0) {
													v(a -= 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 13) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main2")
																	.removeClass(
																		"z-index")
															})
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main2")
																	.removeClass(
																		"z-index")
															})
												}
												$(
														".select-arrow2")
													.removeClass(
														"reverse");
												$(document)
													.unbind(
														"keydown");
												e
													.preventDefault()
											}
										})
							}
						} else {
							if(h == "slide") {
								i.slideUp(l / 2, function() {
									$(".select-main2").removeClass(
										"z-index")
								});
								$(".select-arrow2").removeClass(
									"reverse")
							} else if(h == "fade") {
								i.fadeOut(l / 2, function() {
									$(".select-main2").removeClass(
										"z-index")
								});
								$(".select-arrow2").removeClass(
									"reverse")
							}
							$(document).unbind("keydown")
						}
					})
			}
		}
		);
		//襄州区
"use strict";
$
	.widget(
		"ui.selectWidget3", {
			options: {
				change: function(e) {
					return e
				},
				effect: "slide",
				keyControl: true,
				speed: 200,
				scrollHeight: 250
			},
			_create: function() {
				this._selectFunctional()
			},
			_selectFunctional: function() {
				function v(t) {
					$("li.active", s).removeClass("active");
					$("li", s).eq(t).addClass("active");
					$("option[selected]", e).removeAttr("selected");
					u.eq(t).prop("selected", true);
					e.val(u.eq(t).val());
					n.text(u.eq(t).text());
					a = t;
					if(p) {
						e.change(u.eq(t).val());
						p(e.val())
					}
					return t
				}

				function m() {
					var e = 0,
						t = $(".select-items3", s);
					for(var n = 0, r = t.length; n < r; n++) {
						if($(t[n]).hasClass("active")) {
							break
						}
						e += $(t[n]).outerHeight()
					}
					s.stop().animate({
						scrollTop: e
					}, l)
				}
				if(this.element.is(":hidden")) {
					return false
				}
				var e = this.element,
					t = $("<div>").addClass(
						"select-main3"),
					n = $("<div>").addClass(
						"select-set3"),
					r = $("<div>").addClass(
						"select-arrow3"),
					i = $("<div>").addClass(
						"select-block3"),
					s = $("<ul>").addClass(
						"select-list3"),
					o = e.find("option[selected]").length,
					u = $(
						"option", e),
					a = 0,
					f = $("input[type=reset]",
						e.parents("form")),
					l = this.options.speed,
					c = this.options.keyControl,
					h = this.options.effect,
					p = this.options.change,
					d = this.options.scrollHeight;
				t.append(r);
				t.append(n);
				i.append(s);
				t.append(i);
				e.after(t);
				e.hide();
				i.hide();
				u.each(function(e, t) {
					s.append($("<li>").addClass("select-items3").text(
						$(t).text()))
				});
//				$(".select-items").click(function () {
//					alert($(this).text())
//				})
				
				f.click(function() {
					$(u, $(this).parents("form")).each(function(e, t) {
						if($(t).val() == "" && !$(t).is(":selected")) {
							$(".select-items3:first", s).click();
							return false
						}
					})
				});
				if(e.attr("disabled")) {
					t.addClass("disabled")
				}
				if(!o) {
					e.val(u.first().val());
					//							$("li:first", s).addClass("active");
					n.text(u.first().text())
				} else {
					e.val(e.find("option[selected]").val());
					$("li", s).eq(e.find("option[selected]").index())
						.addClass("active");
					n.text(e.find("option[selected]").text())
				}
				a = $("option[selected]", e).index();
				$("li", s).click(function() {
					var e = $(this).index();
					a = e;
					v(e)
				});
				if(d) {
					if(i.height() > d) {
						s.css("height", d).css("overflow", "auto")
					}
				}
				r.click(function() {
					n.click()
				});
				n
					.click(function() {
						var e = $(this);
						if(e.parent(t).hasClass("disabled")) {
							return false
						}
						if(!e.prev().hasClass("reverse")) {
							$(".select-arrow3").removeClass(
								"reverse");
							e.prev().addClass("reverse");
							e.parent().addClass("z-index")
						} else {
							e.parent().removeClass("z-index");
							e.prev().removeClass("reverse")
						}
						if(i.is(":hidden")) {
							if($(".select-block3").is(":visible")) {
								if(h == "slide") {
									$(".select-block3").slideUp(
										l / 2);
									$(".select-main3").removeClass("z-index")
								} else if(h == "fade") {
									$(".select-block3").fadeOut(l / 2);
									$(".select-main3").removeClass("z-index")
								}
								$(document).unbind("keydown")
							}
							$(document)
								.bind(
									"click",
									function(e) {
										if(!$(e.target)
											.is(
												".select-set3") &&
											!$(
												e.target)
											.is(
												".select-arrow3")) {
											if($(
													".select-block3")
												.is(
													":visible")) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main3")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow3")
														.removeClass(
															"reverse")
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main3")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow3")
														.removeClass(
															"reverse")
												}
												$(document)
													.unbind(
														"click keydown")
											}
										}
									});
							if(h == "slide") {
								i.slideDown(l);
								e.parent().addClass("z-index");
								m()
							} else if(h == "fade") {
								i.fadeIn(l);
								e.parent().addClass("z-index");
								m()
							}
							if(c) {
								$(document)
									.bind(
										"keydown",
										function(e) {
											var t = e.keyCode;
											if(t == 40) {
												if(a < u.length - 1) {
													a = a == -1 ? 0 :
														a;
													v(a += 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 38) {
												if(a > 0) {
													v(a -= 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 13) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main3")
																	.removeClass(
																		"z-index")
															})
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main3")
																	.removeClass(
																		"z-index")
															})
												}
												$(
														".select-arrow3")
													.removeClass(
														"reverse");
												$(document)
													.unbind(
														"keydown");
												e
													.preventDefault()
											}
										})
							}
						} else {
							if(h == "slide") {
								i.slideUp(l / 2, function() {
									$(".select-main3").removeClass(
										"z-index")
								});
								$(".select-arrow3").removeClass(
									"reverse")
							} else if(h == "fade") {
								i.fadeOut(l / 2, function() {
									$(".select-main3").removeClass(
										"z-index")
								});
								$(".select-arrow3").removeClass(
									"reverse")
							}
							$(document).unbind("keydown")
						}
					})
			}
		}
		);
			//樊城区
"use strict";
$
	.widget(
		"ui.selectWidget4", {
			options: {
				change: function(e) {
					return e
				},
				effect: "slide",
				keyControl: true,
				speed: 200,
				scrollHeight: 250
			},
			_create: function() {
				this._selectFunctional()
			},
			_selectFunctional: function() {
				function v(t) {
					$("li.active", s).removeClass("active");
					$("li", s).eq(t).addClass("active");
					$("option[selected]", e).removeAttr("selected");
					u.eq(t).prop("selected", true);
					e.val(u.eq(t).val());
					n.text(u.eq(t).text());
					a = t;
					if(p) {
						e.change(u.eq(t).val());
						p(e.val())
					}
					return t
				}

				function m() {
					var e = 0,
						t = $(".select-items4", s);
					for(var n = 0, r = t.length; n < r; n++) {
						if($(t[n]).hasClass("active")) {
							break
						}
						e += $(t[n]).outerHeight()
					}
					s.stop().animate({
						scrollTop: e
					}, l)
				}
				if(this.element.is(":hidden")) {
					return false
				}
				var e = this.element,
					t = $("<div>").addClass(
						"select-main4"),
					n = $("<div>").addClass(
						"select-set4"),
					r = $("<div>").addClass(
						"select-arrow4"),
					i = $("<div>").addClass(
						"select-block4"),
					s = $("<ul>").addClass(
						"select-list4"),
					o = e.find("option[selected]").length,
					u = $(
						"option", e),
					a = 0,
					f = $("input[type=reset]",
						e.parents("form")),
					l = this.options.speed,
					c = this.options.keyControl,
					h = this.options.effect,
					p = this.options.change,
					d = this.options.scrollHeight;
				t.append(r);
				t.append(n);
				i.append(s);
				t.append(i);
				e.after(t);
				e.hide();
				i.hide();
				u.each(function(e, t) {
					s.append($("<li>").addClass("select-items4").text(
						$(t).text()))
				});
//				$(".select-items").click(function () {
//					alert($(this).text())
//				})
				
				f.click(function() {
					$(u, $(this).parents("form")).each(function(e, t) {
						if($(t).val() == "" && !$(t).is(":selected")) {
							$(".select-items4:first", s).click();
							return false
						}
					})
				});
				if(e.attr("disabled")) {
					t.addClass("disabled")
				}
				if(!o) {
					e.val(u.first().val());
					//							$("li:first", s).addClass("active");
					n.text(u.first().text())
				} else {
					e.val(e.find("option[selected]").val());
					$("li", s).eq(e.find("option[selected]").index())
						.addClass("active");
					n.text(e.find("option[selected]").text())
				}
				a = $("option[selected]", e).index();
				$("li", s).click(function() {
					var e = $(this).index();
					a = e;
					v(e)
				});
				if(d) {
					if(i.height() > d) {
						s.css("height", d).css("overflow", "auto")
					}
				}
				r.click(function() {
					n.click()
				});
				n
					.click(function() {
						var e = $(this);
						if(e.parent(t).hasClass("disabled")) {
							return false
						}
						if(!e.prev().hasClass("reverse")) {
							$(".select-arrow4").removeClass(
								"reverse");
							e.prev().addClass("reverse");
							e.parent().addClass("z-index")
						} else {
							e.parent().removeClass("z-index");
							e.prev().removeClass("reverse")
						}
						if(i.is(":hidden")) {
							if($(".select-block4").is(":visible")) {
								if(h == "slide") {
									$(".select-block4").slideUp(
										l / 2);
									$(".select-main4").removeClass("z-index")
								} else if(h == "fade") {
									$(".select-block4").fadeOut(l / 2);
									$(".select-main4").removeClass("z-index")
								}
								$(document).unbind("keydown")
							}
							$(document)
								.bind(
									"click",
									function(e) {
										if(!$(e.target)
											.is(
												".select-set4") &&
											!$(
												e.target)
											.is(
												".select-arrow4")) {
											if($(
													".select-block4")
												.is(
													":visible")) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main4")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow4")
														.removeClass(
															"reverse")
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main4")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow4")
														.removeClass(
															"reverse")
												}
												$(document)
													.unbind(
														"click keydown")
											}
										}
									});
							if(h == "slide") {
								i.slideDown(l);
								e.parent().addClass("z-index");
								m()
							} else if(h == "fade") {
								i.fadeIn(l);
								e.parent().addClass("z-index");
								m()
							}
							if(c) {
								$(document)
									.bind(
										"keydown",
										function(e) {
											var t = e.keyCode;
											if(t == 40) {
												if(a < u.length - 1) {
													a = a == -1 ? 0 :
														a;
													v(a += 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 38) {
												if(a > 0) {
													v(a -= 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 13) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main4")
																	.removeClass(
																		"z-index")
															})
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main4")
																	.removeClass(
																		"z-index")
															})
												}
												$(
														".select-arrow4")
													.removeClass(
														"reverse");
												$(document)
													.unbind(
														"keydown");
												e
													.preventDefault()
											}
										})
							}
						} else {
							if(h == "slide") {
								i.slideUp(l / 2, function() {
									$(".select-main4").removeClass(
										"z-index")
								});
								$(".select-arrow4").removeClass(
									"reverse")
							} else if(h == "fade") {
								i.fadeOut(l / 2, function() {
									$(".select-main4").removeClass(
										"z-index")
								});
								$(".select-arrow4").removeClass(
									"reverse")
							}
							$(document).unbind("keydown")
						}
					})
			}
		}
		);
			//宜城市
"use strict";
$
	.widget(
		"ui.selectWidget5", {
			options: {
				change: function(e) {
					return e
				},
				effect: "slide",
				keyControl: true,
				speed: 200,
				scrollHeight: 250
			},
			_create: function() {
				this._selectFunctional()
			},
			_selectFunctional: function() {
				function v(t) {
					$("li.active", s).removeClass("active");
					$("li", s).eq(t).addClass("active");
					$("option[selected]", e).removeAttr("selected");
					u.eq(t).prop("selected", true);
					e.val(u.eq(t).val());
					n.text(u.eq(t).text());
					a = t;
					if(p) {
						e.change(u.eq(t).val());
						p(e.val())
					}
					return t
				}

				function m() {
					var e = 0,
						t = $(".select-items5", s);
					for(var n = 0, r = t.length; n < r; n++) {
						if($(t[n]).hasClass("active")) {
							break
						}
						e += $(t[n]).outerHeight()
					}
					s.stop().animate({
						scrollTop: e
					}, l)
				}
				if(this.element.is(":hidden")) {
					return false
				}
				var e = this.element,
					t = $("<div>").addClass(
						"select-main5"),
					n = $("<div>").addClass(
						"select-set5"),
					r = $("<div>").addClass(
						"select-arrow5"),
					i = $("<div>").addClass(
						"select-block5"),
					s = $("<ul>").addClass(
						"select-list5"),
					o = e.find("option[selected]").length,
					u = $(
						"option", e),
					a = 0,
					f = $("input[type=reset]",
						e.parents("form")),
					l = this.options.speed,
					c = this.options.keyControl,
					h = this.options.effect,
					p = this.options.change,
					d = this.options.scrollHeight;
				t.append(r);
				t.append(n);
				i.append(s);
				t.append(i);
				e.after(t);
				e.hide();
				i.hide();
				u.each(function(e, t) {
					s.append($("<li>").addClass("select-items5").text(
						$(t).text()))
				});
//				$(".select-items").click(function () {
//					alert($(this).text())
//				})
				
				f.click(function() {
					$(u, $(this).parents("form")).each(function(e, t) {
						if($(t).val() == "" && !$(t).is(":selected")) {
							$(".select-items5:first", s).click();
							return false
						}
					})
				});
				if(e.attr("disabled")) {
					t.addClass("disabled")
				}
				if(!o) {
					e.val(u.first().val());
					//							$("li:first", s).addClass("active");
					n.text(u.first().text())
				} else {
					e.val(e.find("option[selected]").val());
					$("li", s).eq(e.find("option[selected]").index())
						.addClass("active");
					n.text(e.find("option[selected]").text())
				}
				a = $("option[selected]", e).index();
				$("li", s).click(function() {
					var e = $(this).index();
					a = e;
					v(e)
				});
				if(d) {
					if(i.height() > d) {
						s.css("height", d).css("overflow", "auto")
					}
				}
				r.click(function() {
					n.click()
				});
				n
					.click(function() {
						var e = $(this);
						if(e.parent(t).hasClass("disabled")) {
							return false
						}
						if(!e.prev().hasClass("reverse")) {
							$(".select-arrow5").removeClass(
								"reverse");
							e.prev().addClass("reverse");
							e.parent().addClass("z-index")
						} else {
							e.parent().removeClass("z-index");
							e.prev().removeClass("reverse")
						}
						if(i.is(":hidden")) {
							if($(".select-block5").is(":visible")) {
								if(h == "slide") {
									$(".select-block5").slideUp(
										l / 2);
									$(".select-main5").removeClass("z-index")
								} else if(h == "fade") {
									$(".select-block5").fadeOut(l / 2);
									$(".select-main5").removeClass("z-index")
								}
								$(document).unbind("keydown")
							}
							$(document)
								.bind(
									"click",
									function(e) {
										if(!$(e.target)
											.is(
												".select-set5") &&
											!$(
												e.target)
											.is(
												".select-arrow5")) {
											if($(
													".select-block5")
												.is(
													":visible")) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main5")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow5")
														.removeClass(
															"reverse")
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main5")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow5")
														.removeClass(
															"reverse")
												}
												$(document)
													.unbind(
														"click keydown")
											}
										}
									});
							if(h == "slide") {
								i.slideDown(l);
								e.parent().addClass("z-index");
								m()
							} else if(h == "fade") {
								i.fadeIn(l);
								e.parent().addClass("z-index");
								m()
							}
							if(c) {
								$(document)
									.bind(
										"keydown",
										function(e) {
											var t = e.keyCode;
											if(t == 40) {
												if(a < u.length - 1) {
													a = a == -1 ? 0 :
														a;
													v(a += 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 38) {
												if(a > 0) {
													v(a -= 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 13) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main5")
																	.removeClass(
																		"z-index")
															})
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main5")
																	.removeClass(
																		"z-index")
															})
												}
												$(
														".select-arrow5")
													.removeClass(
														"reverse");
												$(document)
													.unbind(
														"keydown");
												e
													.preventDefault()
											}
										})
							}
						} else {
							if(h == "slide") {
								i.slideUp(l / 2, function() {
									$(".select-main5").removeClass(
										"z-index")
								});
								$(".select-arrow5").removeClass(
									"reverse")
							} else if(h == "fade") {
								i.fadeOut(l / 2, function() {
									$(".select-main5").removeClass(
										"z-index")
								});
								$(".select-arrow5").removeClass(
									"reverse")
							}
							$(document).unbind("keydown")
						}
					})
			}
		}
		);
	//襄城区
"use strict";
$
	.widget(
		"ui.selectWidget6", {
			options: {
				change: function(e) {
					return e
				},
				effect: "slide",
				keyControl: true,
				speed: 200,
				scrollHeight: 250
			},
			_create: function() {
				this._selectFunctional()
			},
			_selectFunctional: function() {
				function v(t) {
					$("li.active", s).removeClass("active");
					$("li", s).eq(t).addClass("active");
					$("option[selected]", e).removeAttr("selected");
					u.eq(t).prop("selected", true);
					e.val(u.eq(t).val());
					n.text(u.eq(t).text());
					a = t;
					if(p) {
						e.change(u.eq(t).val());
						p(e.val())
					}
					return t
				}

				function m() {
					var e = 0,
						t = $(".select-items6", s);
					for(var n = 0, r = t.length; n < r; n++) {
						if($(t[n]).hasClass("active")) {
							break
						}
						e += $(t[n]).outerHeight()
					}
					s.stop().animate({
						scrollTop: e
					}, l)
				}
				if(this.element.is(":hidden")) {
					return false
				}
				var e = this.element,
					t = $("<div>").addClass(
						"select-main6"),
					n = $("<div>").addClass(
						"select-set6"),
					r = $("<div>").addClass(
						"select-arrow6"),
					i = $("<div>").addClass(
						"select-block6"),
					s = $("<ul>").addClass(
						"select-list6"),
					o = e.find("option[selected]").length,
					u = $(
						"option", e),
					a = 0,
					f = $("input[type=reset]",
						e.parents("form")),
					l = this.options.speed,
					c = this.options.keyControl,
					h = this.options.effect,
					p = this.options.change,
					d = this.options.scrollHeight;
				t.append(r);
				t.append(n);
				i.append(s);
				t.append(i);
				e.after(t);
				e.hide();
				i.hide();
				u.each(function(e, t) {
					s.append($("<li>").addClass("select-items6").text(
						$(t).text()))
				});
//				$(".select-items").click(function () {
//					alert($(this).text())
//				})
				
				f.click(function() {
					$(u, $(this).parents("form")).each(function(e, t) {
						if($(t).val() == "" && !$(t).is(":selected")) {
							$(".select-items6:first", s).click();
							return false
						}
					})
				});
				if(e.attr("disabled")) {
					t.addClass("disabled")
				}
				if(!o) {
					e.val(u.first().val());
					//							$("li:first", s).addClass("active");
					n.text(u.first().text())
				} else {
					e.val(e.find("option[selected]").val());
					$("li", s).eq(e.find("option[selected]").index())
						.addClass("active");
					n.text(e.find("option[selected]").text())
				}
				a = $("option[selected]", e).index();
				$("li", s).click(function() {
					var e = $(this).index();
					a = e;
					v(e)
				});
				if(d) {
					if(i.height() > d) {
						s.css("height", d).css("overflow", "auto")
					}
				}
				r.click(function() {
					n.click()
				});
				n
					.click(function() {
						var e = $(this);
						if(e.parent(t).hasClass("disabled")) {
							return false
						}
						if(!e.prev().hasClass("reverse")) {
							$(".select-arrow6").removeClass(
								"reverse");
							e.prev().addClass("reverse");
							e.parent().addClass("z-index")
						} else {
							e.parent().removeClass("z-index");
							e.prev().removeClass("reverse")
						}
						if(i.is(":hidden")) {
							if($(".select-block6").is(":visible")) {
								if(h == "slide") {
									$(".select-block6").slideUp(
										l / 2);
									$(".select-main6").removeClass("z-index")
								} else if(h == "fade") {
									$(".select-block6").fadeOut(l / 2);
									$(".select-main6").removeClass("z-index")
								}
								$(document).unbind("keydown")
							}
							$(document)
								.bind(
									"click",
									function(e) {
										if(!$(e.target)
											.is(
												".select-set6") &&
											!$(
												e.target)
											.is(
												".select-arrow6")) {
											if($(
													".select-block6")
												.is(
													":visible")) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main6")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow6")
														.removeClass(
															"reverse")
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main6")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow6")
														.removeClass(
															"reverse")
												}
												$(document)
													.unbind(
														"click keydown")
											}
										}
									});
							if(h == "slide") {
								i.slideDown(l);
								e.parent().addClass("z-index");
								m()
							} else if(h == "fade") {
								i.fadeIn(l);
								e.parent().addClass("z-index");
								m()
							}
							if(c) {
								$(document)
									.bind(
										"keydown",
										function(e) {
											var t = e.keyCode;
											if(t == 40) {
												if(a < u.length - 1) {
													a = a == -1 ? 0 :
														a;
													v(a += 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 38) {
												if(a > 0) {
													v(a -= 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 13) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main6")
																	.removeClass(
																		"z-index")
															})
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main6")
																	.removeClass(
																		"z-index")
															})
												}
												$(
														".select-arrow6")
													.removeClass(
														"reverse");
												$(document)
													.unbind(
														"keydown");
												e
													.preventDefault()
											}
										})
							}
						} else {
							if(h == "slide") {
								i.slideUp(l / 2, function() {
									$(".select-main6").removeClass(
										"z-index")
								});
								$(".select-arrow6").removeClass(
									"reverse")
							} else if(h == "fade") {
								i.fadeOut(l / 2, function() {
									$(".select-main6").removeClass(
										"z-index")
								});
								$(".select-arrow6").removeClass(
									"reverse")
							}
							$(document).unbind("keydown")
						}
					})
			}
		}
		);
					//宜城市
"use strict";
$
	.widget(
		"ui.selectWidget5", {
			options: {
				change: function(e) {
					return e
				},
				effect: "slide",
				keyControl: true,
				speed: 200,
				scrollHeight: 250
			},
			_create: function() {
				this._selectFunctional()
			},
			_selectFunctional: function() {
				function v(t) {
					$("li.active", s).removeClass("active");
					$("li", s).eq(t).addClass("active");
					$("option[selected]", e).removeAttr("selected");
					u.eq(t).prop("selected", true);
					e.val(u.eq(t).val());
					n.text(u.eq(t).text());
					a = t;
					if(p) {
						e.change(u.eq(t).val());
						p(e.val())
					}
					return t
				}

				function m() {
					var e = 0,
						t = $(".select-items5", s);
					for(var n = 0, r = t.length; n < r; n++) {
						if($(t[n]).hasClass("active")) {
							break
						}
						e += $(t[n]).outerHeight()
					}
					s.stop().animate({
						scrollTop: e
					}, l)
				}
				if(this.element.is(":hidden")) {
					return false
				}
				var e = this.element,
					t = $("<div>").addClass(
						"select-main5"),
					n = $("<div>").addClass(
						"select-set5"),
					r = $("<div>").addClass(
						"select-arrow5"),
					i = $("<div>").addClass(
						"select-block5"),
					s = $("<ul>").addClass(
						"select-list5"),
					o = e.find("option[selected]").length,
					u = $(
						"option", e),
					a = 0,
					f = $("input[type=reset]",
						e.parents("form")),
					l = this.options.speed,
					c = this.options.keyControl,
					h = this.options.effect,
					p = this.options.change,
					d = this.options.scrollHeight;
				t.append(r);
				t.append(n);
				i.append(s);
				t.append(i);
				e.after(t);
				e.hide();
				i.hide();
				u.each(function(e, t) {
					s.append($("<li>").addClass("select-items5").text(
						$(t).text()))
				});
//				$(".select-items").click(function () {
//					alert($(this).text())
//				})
				
				f.click(function() {
					$(u, $(this).parents("form")).each(function(e, t) {
						if($(t).val() == "" && !$(t).is(":selected")) {
							$(".select-items5:first", s).click();
							return false
						}
					})
				});
				if(e.attr("disabled")) {
					t.addClass("disabled")
				}
				if(!o) {
					e.val(u.first().val());
					//							$("li:first", s).addClass("active");
					n.text(u.first().text())
				} else {
					e.val(e.find("option[selected]").val());
					$("li", s).eq(e.find("option[selected]").index())
						.addClass("active");
					n.text(e.find("option[selected]").text())
				}
				a = $("option[selected]", e).index();
				$("li", s).click(function() {
					var e = $(this).index();
					a = e;
					v(e)
				});
				if(d) {
					if(i.height() > d) {
						s.css("height", d).css("overflow", "auto")
					}
				}
				r.click(function() {
					n.click()
				});
				n
					.click(function() {
						var e = $(this);
						if(e.parent(t).hasClass("disabled")) {
							return false
						}
						if(!e.prev().hasClass("reverse")) {
							$(".select-arrow5").removeClass(
								"reverse");
							e.prev().addClass("reverse");
							e.parent().addClass("z-index")
						} else {
							e.parent().removeClass("z-index");
							e.prev().removeClass("reverse")
						}
						if(i.is(":hidden")) {
							if($(".select-block5").is(":visible")) {
								if(h == "slide") {
									$(".select-block5").slideUp(
										l / 2);
									$(".select-main5").removeClass("z-index")
								} else if(h == "fade") {
									$(".select-block5").fadeOut(l / 2);
									$(".select-main5").removeClass("z-index")
								}
								$(document).unbind("keydown")
							}
							$(document)
								.bind(
									"click",
									function(e) {
										if(!$(e.target)
											.is(
												".select-set5") &&
											!$(
												e.target)
											.is(
												".select-arrow5")) {
											if($(
													".select-block5")
												.is(
													":visible")) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main5")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow5")
														.removeClass(
															"reverse")
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main5")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow5")
														.removeClass(
															"reverse")
												}
												$(document)
													.unbind(
														"click keydown")
											}
										}
									});
							if(h == "slide") {
								i.slideDown(l);
								e.parent().addClass("z-index");
								m()
							} else if(h == "fade") {
								i.fadeIn(l);
								e.parent().addClass("z-index");
								m()
							}
							if(c) {
								$(document)
									.bind(
										"keydown",
										function(e) {
											var t = e.keyCode;
											if(t == 40) {
												if(a < u.length - 1) {
													a = a == -1 ? 0 :
														a;
													v(a += 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 38) {
												if(a > 0) {
													v(a -= 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 13) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main5")
																	.removeClass(
																		"z-index")
															})
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main5")
																	.removeClass(
																		"z-index")
															})
												}
												$(
														".select-arrow5")
													.removeClass(
														"reverse");
												$(document)
													.unbind(
														"keydown");
												e
													.preventDefault()
											}
										})
							}
						} else {
							if(h == "slide") {
								i.slideUp(l / 2, function() {
									$(".select-main5").removeClass(
										"z-index")
								});
								$(".select-arrow5").removeClass(
									"reverse")
							} else if(h == "fade") {
								i.fadeOut(l / 2, function() {
									$(".select-main5").removeClass(
										"z-index")
								});
								$(".select-arrow5").removeClass(
									"reverse")
							}
							$(document).unbind("keydown")
						}
					})
			}
		}
		);
	//南漳县
"use strict";
$
	.widget(
		"ui.selectWidget7", {
			options: {
				change: function(e) {
					return e
				},
				effect: "slide",
				keyControl: true,
				speed: 200,
				scrollHeight: 250
			},
			_create: function() {
				this._selectFunctional()
			},
			_selectFunctional: function() {
				function v(t) {
					$("li.active", s).removeClass("active");
					$("li", s).eq(t).addClass("active");
					$("option[selected]", e).removeAttr("selected");
					u.eq(t).prop("selected", true);
					e.val(u.eq(t).val());
					n.text(u.eq(t).text());
					a = t;
					if(p) {
						e.change(u.eq(t).val());
						p(e.val())
					}
					return t
				}

				function m() {
					var e = 0,
						t = $(".select-items7", s);
					for(var n = 0, r = t.length; n < r; n++) {
						if($(t[n]).hasClass("active")) {
							break
						}
						e += $(t[n]).outerHeight()
					}
					s.stop().animate({
						scrollTop: e
					}, l)
				}
				if(this.element.is(":hidden")) {
					return false
				}
				var e = this.element,
					t = $("<div>").addClass(
						"select-main7"),
					n = $("<div>").addClass(
						"select-set7"),
					r = $("<div>").addClass(
						"select-arrow7"),
					i = $("<div>").addClass(
						"select-block7"),
					s = $("<ul>").addClass(
						"select-list7"),
					o = e.find("option[selected]").length,
					u = $(
						"option", e),
					a = 0,
					f = $("input[type=reset]",
						e.parents("form")),
					l = this.options.speed,
					c = this.options.keyControl,
					h = this.options.effect,
					p = this.options.change,
					d = this.options.scrollHeight;
				t.append(r);
				t.append(n);
				i.append(s);
				t.append(i);
				e.after(t);
				e.hide();
				i.hide();
				u.each(function(e, t) {
					s.append($("<li>").addClass("select-items7").text(
						$(t).text()))
				});
//				$(".select-items").click(function () {
//					alert($(this).text())
//				})
				
				f.click(function() {
					$(u, $(this).parents("form")).each(function(e, t) {
						if($(t).val() == "" && !$(t).is(":selected")) {
							$(".select-items7:first", s).click();
							return false
						}
					})
				});
				if(e.attr("disabled")) {
					t.addClass("disabled")
				}
				if(!o) {
					e.val(u.first().val());
					//							$("li:first", s).addClass("active");
					n.text(u.first().text())
				} else {
					e.val(e.find("option[selected]").val());
					$("li", s).eq(e.find("option[selected]").index())
						.addClass("active");
					n.text(e.find("option[selected]").text())
				}
				a = $("option[selected]", e).index();
				$("li", s).click(function() {
					var e = $(this).index();
					a = e;
					v(e)
				});
				if(d) {
					if(i.height() > d) {
						s.css("height", d).css("overflow", "auto")
					}
				}
				r.click(function() {
					n.click()
				});
				n
					.click(function() {
						var e = $(this);
						if(e.parent(t).hasClass("disabled")) {
							return false
						}
						if(!e.prev().hasClass("reverse")) {
							$(".select-arrow7").removeClass(
								"reverse");
							e.prev().addClass("reverse");
							e.parent().addClass("z-index")
						} else {
							e.parent().removeClass("z-index");
							e.prev().removeClass("reverse")
						}
						if(i.is(":hidden")) {
							if($(".select-block7").is(":visible")) {
								if(h == "slide") {
									$(".select-block7").slideUp(
										l / 2);
									$(".select-main7").removeClass("z-index")
								} else if(h == "fade") {
									$(".select-block7").fadeOut(l / 2);
									$(".select-main7").removeClass("z-index")
								}
								$(document).unbind("keydown")
							}
							$(document)
								.bind(
									"click",
									function(e) {
										if(!$(e.target)
											.is(
												".select-set7") &&
											!$(
												e.target)
											.is(
												".select-arrow7")) {
											if($(
													".select-block7")
												.is(
													":visible")) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main7")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow7")
														.removeClass(
															"reverse")
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main7")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow7")
														.removeClass(
															"reverse")
												}
												$(document)
													.unbind(
														"click keydown")
											}
										}
									});
							if(h == "slide") {
								i.slideDown(l);
								e.parent().addClass("z-index");
								m()
							} else if(h == "fade") {
								i.fadeIn(l);
								e.parent().addClass("z-index");
								m()
							}
							if(c) {
								$(document)
									.bind(
										"keydown",
										function(e) {
											var t = e.keyCode;
											if(t == 40) {
												if(a < u.length - 1) {
													a = a == -1 ? 0 :
														a;
													v(a += 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 38) {
												if(a > 0) {
													v(a -= 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 13) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main7")
																	.removeClass(
																		"z-index")
															})
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main7")
																	.removeClass(
																		"z-index")
															})
												}
												$(
														".select-arrow7")
													.removeClass(
														"reverse");
												$(document)
													.unbind(
														"keydown");
												e
													.preventDefault()
											}
										})
							}
						} else {
							if(h == "slide") {
								i.slideUp(l / 2, function() {
									$(".select-main7").removeClass(
										"z-index")
								});
								$(".select-arrow7").removeClass(
									"reverse")
							} else if(h == "fade") {
								i.fadeOut(l / 2, function() {
									$(".select-main7").removeClass(
										"z-index")
								});
								$(".select-arrow7").removeClass(
									"reverse")
							}
							$(document).unbind("keydown")
						}
					})
			}
		}
		);
			//枣阳市
"use strict";
$
	.widget(
		"ui.selectWidget8", {
			options: {
				change: function(e) {
					return e
				},
				effect: "slide",
				keyControl: true,
				speed: 200,
				scrollHeight: 250
			},
			_create: function() {
				this._selectFunctional()
			},
			_selectFunctional: function() {
				function v(t) {
					$("li.active", s).removeClass("active");
					$("li", s).eq(t).addClass("active");
					$("option[selected]", e).removeAttr("selected");
					u.eq(t).prop("selected", true);
					e.val(u.eq(t).val());
					n.text(u.eq(t).text());
					a = t;
					if(p) {
						e.change(u.eq(t).val());
						p(e.val())
					}
					return t
				}

				function m() {
					var e = 0,
						t = $(".select-items8", s);
					for(var n = 0, r = t.length; n < r; n++) {
						if($(t[n]).hasClass("active")) {
							break
						}
						e += $(t[n]).outerHeight()
					}
					s.stop().animate({
						scrollTop: e
					}, l)
				}
				if(this.element.is(":hidden")) {
					return false
				}
				var e = this.element,
					t = $("<div>").addClass(
						"select-main8"),
					n = $("<div>").addClass(
						"select-set8"),
					r = $("<div>").addClass(
						"select-arrow8"),
					i = $("<div>").addClass(
						"select-block8"),
					s = $("<ul>").addClass(
						"select-list8"),
					o = e.find("option[selected]").length,
					u = $(
						"option", e),
					a = 0,
					f = $("input[type=reset]",
						e.parents("form")),
					l = this.options.speed,
					c = this.options.keyControl,
					h = this.options.effect,
					p = this.options.change,
					d = this.options.scrollHeight;
				t.append(r);
				t.append(n);
				i.append(s);
				t.append(i);
				e.after(t);
				e.hide();
				i.hide();
				u.each(function(e, t) {
					s.append($("<li>").addClass("select-items8").text(
						$(t).text()))
				});
//				$(".select-items").click(function () {
//					alert($(this).text())
//				})
				
				f.click(function() {
					$(u, $(this).parents("form")).each(function(e, t) {
						if($(t).val() == "" && !$(t).is(":selected")) {
							$(".select-items8:first", s).click();
							return false
						}
					})
				});
				if(e.attr("disabled")) {
					t.addClass("disabled")
				}
				if(!o) {
					e.val(u.first().val());
					//							$("li:first", s).addClass("active");
					n.text(u.first().text())
				} else {
					e.val(e.find("option[selected]").val());
					$("li", s).eq(e.find("option[selected]").index())
						.addClass("active");
					n.text(e.find("option[selected]").text())
				}
				a = $("option[selected]", e).index();
				$("li", s).click(function() {
					var e = $(this).index();
					a = e;
					v(e)
				});
				if(d) {
					if(i.height() > d) {
						s.css("height", d).css("overflow", "auto")
					}
				}
				r.click(function() {
					n.click()
				});
				n
					.click(function() {
						var e = $(this);
						if(e.parent(t).hasClass("disabled")) {
							return false
						}
						if(!e.prev().hasClass("reverse")) {
							$(".select-arrow8").removeClass(
								"reverse");
							e.prev().addClass("reverse");
							e.parent().addClass("z-index")
						} else {
							e.parent().removeClass("z-index");
							e.prev().removeClass("reverse")
						}
						if(i.is(":hidden")) {
							if($(".select-block8").is(":visible")) {
								if(h == "slide") {
									$(".select-block8").slideUp(
										l / 2);
									$(".select-main8").removeClass("z-index")
								} else if(h == "fade") {
									$(".select-block8").fadeOut(l / 2);
									$(".select-main8").removeClass("z-index")
								}
								$(document).unbind("keydown")
							}
							$(document)
								.bind(
									"click",
									function(e) {
										if(!$(e.target)
											.is(
												".select-set8") &&
											!$(
												e.target)
											.is(
												".select-arrow8")) {
											if($(
													".select-block8")
												.is(
													":visible")) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main8")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow8")
														.removeClass(
															"reverse")
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main8")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow8")
														.removeClass(
															"reverse")
												}
												$(document)
													.unbind(
														"click keydown")
											}
										}
									});
							if(h == "slide") {
								i.slideDown(l);
								e.parent().addClass("z-index");
								m()
							} else if(h == "fade") {
								i.fadeIn(l);
								e.parent().addClass("z-index");
								m()
							}
							if(c) {
								$(document)
									.bind(
										"keydown",
										function(e) {
											var t = e.keyCode;
											if(t == 40) {
												if(a < u.length - 1) {
													a = a == -1 ? 0 :
														a;
													v(a += 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 38) {
												if(a > 0) {
													v(a -= 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 13) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main8")
																	.removeClass(
																		"z-index")
															})
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main8")
																	.removeClass(
																		"z-index")
															})
												}
												$(
														".select-arrow8")
													.removeClass(
														"reverse");
												$(document)
													.unbind(
														"keydown");
												e
													.preventDefault()
											}
										})
							}
						} else {
							if(h == "slide") {
								i.slideUp(l / 2, function() {
									$(".select-main8").removeClass(
										"z-index")
								});
								$(".select-arrow8").removeClass(
									"reverse")
							} else if(h == "fade") {
								i.fadeOut(l / 2, function() {
									$(".select-main8").removeClass(
										"z-index")
								});
								$(".select-arrow8").removeClass(
									"reverse")
							}
							$(document).unbind("keydown")
						}
					})
			}
		}
		);
		//高新区
"use strict";
$
	.widget(
		"ui.selectWidget9", {
			options: {
				change: function(e) {
					return e
				},
				effect: "slide",
				keyControl: true,
				speed: 200,
				scrollHeight: 250
			},
			_create: function() {
				this._selectFunctional()
			},
			_selectFunctional: function() {
				function v(t) {
					$("li.active", s).removeClass("active");
					$("li", s).eq(t).addClass("active");
					$("option[selected]", e).removeAttr("selected");
					u.eq(t).prop("selected", true);
					e.val(u.eq(t).val());
					n.text(u.eq(t).text());
					a = t;
					if(p) {
						e.change(u.eq(t).val());
						p(e.val())
					}
					return t
				}

				function m() {
					var e = 0,
						t = $(".select-items9", s);
					for(var n = 0, r = t.length; n < r; n++) {
						if($(t[n]).hasClass("active")) {
							break
						}
						e += $(t[n]).outerHeight()
					}
					s.stop().animate({
						scrollTop: e
					}, l)
				}
				if(this.element.is(":hidden")) {
					return false
				}
				var e = this.element,
					t = $("<div>").addClass(
						"select-main9"),
					n = $("<div>").addClass(
						"select-set9"),
					r = $("<div>").addClass(
						"select-arrow9"),
					i = $("<div>").addClass(
						"select-block9"),
					s = $("<ul>").addClass(
						"select-list9"),
					o = e.find("option[selected]").length,
					u = $(
						"option", e),
					a = 0,
					f = $("input[type=reset]",
						e.parents("form")),
					l = this.options.speed,
					c = this.options.keyControl,
					h = this.options.effect,
					p = this.options.change,
					d = this.options.scrollHeight;
				t.append(r);
				t.append(n);
				i.append(s);
				t.append(i);
				e.after(t);
				e.hide();
				i.hide();
				u.each(function(e, t) {
					s.append($("<li>").addClass("select-items9").text(
						$(t).text()))
				});
//				$(".select-items").click(function () {
//					alert($(this).text())
//				})
				
				f.click(function() {
					$(u, $(this).parents("form")).each(function(e, t) {
						if($(t).val() == "" && !$(t).is(":selected")) {
							$(".select-items9:first", s).click();
							return false
						}
					})
				});
				if(e.attr("disabled")) {
					t.addClass("disabled")
				}
				if(!o) {
					e.val(u.first().val());
					//							$("li:first", s).addClass("active");
					n.text(u.first().text())
				} else {
					e.val(e.find("option[selected]").val());
					$("li", s).eq(e.find("option[selected]").index())
						.addClass("active");
					n.text(e.find("option[selected]").text())
				}
				a = $("option[selected]", e).index();
				$("li", s).click(function() {
					var e = $(this).index();
					a = e;
					v(e)
				});
				if(d) {
					if(i.height() > d) {
						s.css("height", d).css("overflow", "auto")
					}
				}
				r.click(function() {
					n.click()
				});
				n
					.click(function() {
						var e = $(this);
						if(e.parent(t).hasClass("disabled")) {
							return false
						}
						if(!e.prev().hasClass("reverse")) {
							$(".select-arrow9").removeClass(
								"reverse");
							e.prev().addClass("reverse");
							e.parent().addClass("z-index")
						} else {
							e.parent().removeClass("z-index");
							e.prev().removeClass("reverse")
						}
						if(i.is(":hidden")) {
							if($(".select-block9").is(":visible")) {
								if(h == "slide") {
									$(".select-block9").slideUp(
										l / 2);
									$(".select-main9").removeClass("z-index")
								} else if(h == "fade") {
									$(".select-block9").fadeOut(l / 2);
									$(".select-main9").removeClass("z-index")
								}
								$(document).unbind("keydown")
							}
							$(document)
								.bind(
									"click",
									function(e) {
										if(!$(e.target)
											.is(
												".select-set9") &&
											!$(
												e.target)
											.is(
												".select-arrow9")) {
											if($(
													".select-block9")
												.is(
													":visible")) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main9")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow9")
														.removeClass(
															"reverse")
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main9")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow9")
														.removeClass(
															"reverse")
												}
												$(document)
													.unbind(
														"click keydown")
											}
										}
									});
							if(h == "slide") {
								i.slideDown(l);
								e.parent().addClass("z-index");
								m()
							} else if(h == "fade") {
								i.fadeIn(l);
								e.parent().addClass("z-index");
								m()
							}
							if(c) {
								$(document)
									.bind(
										"keydown",
										function(e) {
											var t = e.keyCode;
											if(t == 40) {
												if(a < u.length - 1) {
													a = a == -1 ? 0 :
														a;
													v(a += 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 38) {
												if(a > 0) {
													v(a -= 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 13) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main9")
																	.removeClass(
																		"z-index")
															})
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main9")
																	.removeClass(
																		"z-index")
															})
												}
												$(
														".select-arrow9")
													.removeClass(
														"reverse");
												$(document)
													.unbind(
														"keydown");
												e
													.preventDefault()
											}
										})
							}
						} else {
							if(h == "slide") {
								i.slideUp(l / 2, function() {
									$(".select-main9").removeClass(
										"z-index")
								});
								$(".select-arrow9").removeClass(
									"reverse")
							} else if(h == "fade") {
								i.fadeOut(l / 2, function() {
									$(".select-main9").removeClass(
										"z-index")
								});
								$(".select-arrow9").removeClass(
									"reverse")
							}
							$(document).unbind("keydown")
						}
					})
			}
		}
		);
		//鱼梁洲
"use strict";
$
	.widget(
		"ui.selectWidget10", {
			options: {
				change: function(e) {
					return e
				},
				effect: "slide",
				keyControl: true,
				speed: 200,
				scrollHeight: 250
			},
			_create: function() {
				this._selectFunctional()
			},
			_selectFunctional: function() {
				function v(t) {
					$("li.active", s).removeClass("active");
					$("li", s).eq(t).addClass("active");
					$("option[selected]", e).removeAttr("selected");
					u.eq(t).prop("selected", true);
					e.val(u.eq(t).val());
					n.text(u.eq(t).text());
					a = t;
					if(p) {
						e.change(u.eq(t).val());
						p(e.val())
					}
					return t
				}

				function m() {
					var e = 0,
						t = $(".select-items10", s);
					for(var n = 0, r = t.length; n < r; n++) {
						if($(t[n]).hasClass("active")) {
							break
						}
						e += $(t[n]).outerHeight()
					}
					s.stop().animate({
						scrollTop: e
					}, l)
				}
				if(this.element.is(":hidden")) {
					return false
				}
				var e = this.element,
					t = $("<div>").addClass(
						"select-main10"),
					n = $("<div>").addClass(
						"select-set10"),
					r = $("<div>").addClass(
						"select-arrow10"),
					i = $("<div>").addClass(
						"select-block10"),
					s = $("<ul>").addClass(
						"select-list10"),
					o = e.find("option[selected]").length,
					u = $(
						"option", e),
					a = 0,
					f = $("input[type=reset]",
						e.parents("form")),
					l = this.options.speed,
					c = this.options.keyControl,
					h = this.options.effect,
					p = this.options.change,
					d = this.options.scrollHeight;
				t.append(r);
				t.append(n);
				i.append(s);
				t.append(i);
				e.after(t);
				e.hide();
				i.hide();
				u.each(function(e, t) {
					s.append($("<li>").addClass("select-items10").text(
						$(t).text()))
				});
//				$(".select-items").click(function () {
//					alert($(this).text())
//				})
				
				f.click(function() {
					$(u, $(this).parents("form")).each(function(e, t) {
						if($(t).val() == "" && !$(t).is(":selected")) {
							$(".select-items10:first", s).click();
							return false
						}
					})
				});
				if(e.attr("disabled")) {
					t.addClass("disabled")
				}
				if(!o) {
					e.val(u.first().val());
					//							$("li:first", s).addClass("active");
					n.text(u.first().text())
				} else {
					e.val(e.find("option[selected]").val());
					$("li", s).eq(e.find("option[selected]").index())
						.addClass("active");
					n.text(e.find("option[selected]").text())
				}
				a = $("option[selected]", e).index();
				$("li", s).click(function() {
					var e = $(this).index();
					a = e;
					v(e)
				});
				if(d) {
					if(i.height() > d) {
						s.css("height", d).css("overflow", "auto")
					}
				}
				r.click(function() {
					n.click()
				});
				n
					.click(function() {
						var e = $(this);
						if(e.parent(t).hasClass("disabled")) {
							return false
						}
						if(!e.prev().hasClass("reverse")) {
							$(".select-arrow10").removeClass(
								"reverse");
							e.prev().addClass("reverse");
							e.parent().addClass("z-index")
						} else {
							e.parent().removeClass("z-index");
							e.prev().removeClass("reverse")
						}
						if(i.is(":hidden")) {
							if($(".select-block10").is(":visible")) {
								if(h == "slide") {
									$(".select-block10").slideUp(
										l / 2);
									$(".select-main10").removeClass("z-index")
								} else if(h == "fade") {
									$(".select-block10").fadeOut(l / 2);
									$(".select-main10").removeClass("z-index")
								}
								$(document).unbind("keydown")
							}
							$(document)
								.bind(
									"click",
									function(e) {
										if(!$(e.target)
											.is(
												".select-set10") &&
											!$(
												e.target)
											.is(
												".select-arrow10")) {
											if($(
													".select-block10")
												.is(
													":visible")) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main10")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow10")
														.removeClass(
															"reverse")
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main10")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow10")
														.removeClass(
															"reverse")
												}
												$(document)
													.unbind(
														"click keydown")
											}
										}
									});
							if(h == "slide") {
								i.slideDown(l);
								e.parent().addClass("z-index");
								m()
							} else if(h == "fade") {
								i.fadeIn(l);
								e.parent().addClass("z-index");
								m()
							}
							if(c) {
								$(document)
									.bind(
										"keydown",
										function(e) {
											var t = e.keyCode;
											if(t == 40) {
												if(a < u.length - 1) {
													a = a == -1 ? 0 :
														a;
													v(a += 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 38) {
												if(a > 0) {
													v(a -= 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 13) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main10")
																	.removeClass(
																		"z-index")
															})
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main10")
																	.removeClass(
																		"z-index")
															})
												}
												$(
														".select-arrow10")
													.removeClass(
														"reverse");
												$(document)
													.unbind(
														"keydown");
												e
													.preventDefault()
											}
										})
							}
						} else {
							if(h == "slide") {
								i.slideUp(l / 2, function() {
									$(".select-main10").removeClass(
										"z-index")
								});
								$(".select-arrow10").removeClass(
									"reverse")
							} else if(h == "fade") {
								i.fadeOut(l / 2, function() {
									$(".select-main10").removeClass(
										"z-index")
								});
								$(".select-arrow10").removeClass(
									"reverse")
							}
							$(document).unbind("keydown")
						}
					})
			}
		}
		);
		//东津新区
"use strict";
$
	.widget(
		"ui.selectWidget11", {
			options: {
				change: function(e) {
					return e
				},
				effect: "slide",
				keyControl: true,
				speed: 200,
				scrollHeight: 250
			},
			_create: function() {
				this._selectFunctional()
			},
			_selectFunctional: function() {
				function v(t) {
					$("li.active", s).removeClass("active");
					$("li", s).eq(t).addClass("active");
					$("option[selected]", e).removeAttr("selected");
					u.eq(t).prop("selected", true);
					e.val(u.eq(t).val());
					n.text(u.eq(t).text());
					a = t;
					if(p) {
						e.change(u.eq(t).val());
						p(e.val())
					}
					return t
				}

				function m() {
					var e = 0,
						t = $(".select-items11", s);
					for(var n = 0, r = t.length; n < r; n++) {
						if($(t[n]).hasClass("active")) {
							break
						}
						e += $(t[n]).outerHeight()
					}
					s.stop().animate({
						scrollTop: e
					}, l)
				}
				if(this.element.is(":hidden")) {
					return false
				}
				var e = this.element,
					t = $("<div>").addClass(
						"select-main11"),
					n = $("<div>").addClass(
						"select-set11"),
					r = $("<div>").addClass(
						"select-arrow11"),
					i = $("<div>").addClass(
						"select-block11"),
					s = $("<ul>").addClass(
						"select-list11"),
					o = e.find("option[selected]").length,
					u = $(
						"option", e),
					a = 0,
					f = $("input[type=reset]",
						e.parents("form")),
					l = this.options.speed,
					c = this.options.keyControl,
					h = this.options.effect,
					p = this.options.change,
					d = this.options.scrollHeight;
				t.append(r);
				t.append(n);
				i.append(s);
				t.append(i);
				e.after(t);
				e.hide();
				i.hide();
				u.each(function(e, t) {
					s.append($("<li>").addClass("select-items11").text(
						$(t).text()))
				});
//				$(".select-items").click(function () {
//					alert($(this).text())
//				})
				
				f.click(function() {
					$(u, $(this).parents("form")).each(function(e, t) {
						if($(t).val() == "" && !$(t).is(":selected")) {
							$(".select-items11:first", s).click();
							return false
						}
					})
				});
				if(e.attr("disabled")) {
					t.addClass("disabled")
				}
				if(!o) {
					e.val(u.first().val());
					//							$("li:first", s).addClass("active");
					n.text(u.first().text())
				} else {
					e.val(e.find("option[selected]").val());
					$("li", s).eq(e.find("option[selected]").index())
						.addClass("active");
					n.text(e.find("option[selected]").text())
				}
				a = $("option[selected]", e).index();
				$("li", s).click(function() {
					var e = $(this).index();
					a = e;
					v(e)
				});
				if(d) {
					if(i.height() > d) {
						s.css("height", d).css("overflow", "auto")
					}
				}
				r.click(function() {
					n.click()
				});
				n
					.click(function() {
						var e = $(this);
						if(e.parent(t).hasClass("disabled")) {
							return false
						}
						if(!e.prev().hasClass("reverse")) {
							$(".select-arrow11").removeClass(
								"reverse");
							e.prev().addClass("reverse");
							e.parent().addClass("z-index")
						} else {
							e.parent().removeClass("z-index");
							e.prev().removeClass("reverse")
						}
						if(i.is(":hidden")) {
							if($(".select-block11").is(":visible")) {
								if(h == "slide") {
									$(".select-block11").slideUp(
										l / 2);
									$(".select-main11").removeClass("z-index")
								} else if(h == "fade") {
									$(".select-block11").fadeOut(l / 2);
									$(".select-main11").removeClass("z-index")
								}
								$(document).unbind("keydown")
							}
							$(document)
								.bind(
									"click",
									function(e) {
										if(!$(e.target)
											.is(
												".select-set11") &&
											!$(
												e.target)
											.is(
												".select-arrow11")) {
											if($(
													".select-block11")
												.is(
													":visible")) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main11")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow11")
														.removeClass(
															"reverse")
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main11")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow11")
														.removeClass(
															"reverse")
												}
												$(document)
													.unbind(
														"click keydown")
											}
										}
									});
							if(h == "slide") {
								i.slideDown(l);
								e.parent().addClass("z-index");
								m()
							} else if(h == "fade") {
								i.fadeIn(l);
								e.parent().addClass("z-index");
								m()
							}
							if(c) {
								$(document)
									.bind(
										"keydown",
										function(e) {
											var t = e.keyCode;
											if(t == 40) {
												if(a < u.length - 1) {
													a = a == -1 ? 0 :
														a;
													v(a += 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 38) {
												if(a > 0) {
													v(a -= 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 13) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main11")
																	.removeClass(
																		"z-index")
															})
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main11")
																	.removeClass(
																		"z-index")
															})
												}
												$(
														".select-arrow11")
													.removeClass(
														"reverse");
												$(document)
													.unbind(
														"keydown");
												e
													.preventDefault()
											}
										})
							}
						} else {
							if(h == "slide") {
								i.slideUp(l / 2, function() {
									$(".select-main11").removeClass(
										"z-index")
								});
								$(".select-arrow11").removeClass(
									"reverse")
							} else if(h == "fade") {
								i.fadeOut(l / 2, function() {
									$(".select-main11").removeClass(
										"z-index")
								});
								$(".select-arrow11").removeClass(
									"reverse")
							}
							$(document).unbind("keydown")
						}
					})
			}
		}
		);
			//整体文档报告
"use strict";
$
	.widget(
		"ui.selectWidget12", {
			options: {
				change: function(e) {
					return e
				},
				effect: "slide",
				keyControl: true,
				speed: 200,
				scrollHeight: 250
			},
			_create: function() {
				this._selectFunctional()
			},
			_selectFunctional: function() {
				function v(t) {
					$("li.active", s).removeClass("active");
					$("li", s).eq(t).addClass("active");
					$("option[selected]", e).removeAttr("selected");
					u.eq(t).prop("selected", true);
					e.val(u.eq(t).val());
					n.text(u.eq(t).text());
					a = t;
					if(p) {
						e.change(u.eq(t).val());
						p(e.val())
					}
					return t
				}

				function m() {
					var e = 0,
						t = $(".select-items12", s);
					for(var n = 0, r = t.length; n < r; n++) {
						if($(t[n]).hasClass("active")) {
							break
						}
						e += $(t[n]).outerHeight()
					}
					s.stop().animate({
						scrollTop: e
					}, l)
				}
				if(this.element.is(":hidden")) {
					return false
				}
				var e = this.element,
					t = $("<div>").addClass(
						"select-main12"),
					n = $("<div>").addClass(
						"select-set12"),
					r = $("<div>").addClass(
						"select-arrow12"),
					i = $("<div>").addClass(
						"select-block12"),
					s = $("<ul>").addClass(
						"select-list12"),
					o = e.find("option[selected]").length,
					u = $(
						"option", e),
					a = 0,
					f = $("input[type=reset]",
						e.parents("form")),
					l = this.options.speed,
					c = this.options.keyControl,
					h = this.options.effect,
					p = this.options.change,
					d = this.options.scrollHeight;
				t.append(r);
				t.append(n);
				i.append(s);
				t.append(i);
				e.after(t);
				e.hide();
				i.hide();
				u.each(function(e, t) {
					s.append($("<li>").addClass("select-items12").text(
						$(t).text()))
				});
//				$(".select-items").click(function () {
//					alert($(this).text())
//				})
				
				f.click(function() {
					$(u, $(this).parents("form")).each(function(e, t) {
						if($(t).val() == "" && !$(t).is(":selected")) {
							$(".select-items12:first", s).click();
							return false
						}
					})
				});
				if(e.attr("disabled")) {
					t.addClass("disabled")
				}
				if(!o) {
					e.val(u.first().val());
					//							$("li:first", s).addClass("active");
					n.text(u.first().text())
				} else {
					e.val(e.find("option[selected]").val());
					$("li", s).eq(e.find("option[selected]").index())
						.addClass("active");
					n.text(e.find("option[selected]").text())
				}
				a = $("option[selected]", e).index();
				$("li", s).click(function() {
					var e = $(this).index();
					a = e;
					v(e)
				});
				if(d) {
					if(i.height() > d) {
						s.css("height", d).css("overflow", "auto")
					}
				}
				r.click(function() {
					n.click()
				});
				n
					.click(function() {
						var e = $(this);
						if(e.parent(t).hasClass("disabled")) {
							return false
						}
						if(!e.prev().hasClass("reverse")) {
							$(".select-arrow12").removeClass(
								"reverse");
							e.prev().addClass("reverse");
							e.parent().addClass("z-index")
						} else {
							e.parent().removeClass("z-index");
							e.prev().removeClass("reverse")
						}
						if(i.is(":hidden")) {
							if($(".select-block12").is(":visible")) {
								if(h == "slide") {
									$(".select-block12").slideUp(
										l / 2);
									$(".select-main12").removeClass("z-index")
								} else if(h == "fade") {
									$(".select-block12").fadeOut(l / 2);
									$(".select-main12").removeClass("z-index")
								}
								$(document).unbind("keydown")
							}
							$(document)
								.bind(
									"click",
									function(e) {
										if(!$(e.target)
											.is(
												".select-set12") &&
											!$(
												e.target)
											.is(
												".select-arrow12")) {
											if($(
													".select-block12")
												.is(
													":visible")) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main12")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow12")
														.removeClass(
															"reverse")
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main12")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow12")
														.removeClass(
															"reverse")
												}
												$(document)
													.unbind(
														"click keydown")
											}
										}
									});
							if(h == "slide") {
								i.slideDown(l);
								e.parent().addClass("z-index");
								m()
							} else if(h == "fade") {
								i.fadeIn(l);
								e.parent().addClass("z-index");
								m()
							}
							if(c) {
								$(document)
									.bind(
										"keydown",
										function(e) {
											var t = e.keyCode;
											if(t == 40) {
												if(a < u.length - 1) {
													a = a == -1 ? 0 :
														a;
													v(a += 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 38) {
												if(a > 0) {
													v(a -= 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 13) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main12")
																	.removeClass(
																		"z-index")
															})
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main12")
																	.removeClass(
																		"z-index")
															})
												}
												$(
														".select-arrow12")
													.removeClass(
														"reverse");
												$(document)
													.unbind(
														"keydown");
												e
													.preventDefault()
											}
										})
							}
						} else {
							if(h == "slide") {
								i.slideUp(l / 2, function() {
									$(".select-main12").removeClass(
										"z-index")
								});
								$(".select-arrow12").removeClass(
									"reverse")
							} else if(h == "fade") {
								i.fadeOut(l / 2, function() {
									$(".select-main12").removeClass(
										"z-index")
								});
								$(".select-arrow12").removeClass(
									"reverse")
							}
							$(document).unbind("keydown")
						}
					})
			}
		}
		);
					//整体表格下载
"use strict";
$
	.widget(
		"ui.selectWidget13", {
			options: {
				change: function(e) {
					return e
				},
				effect: "slide",
				keyControl: true,
				speed: 200,
				scrollHeight: 250
			},
			_create: function() {
				this._selectFunctional()
			},
			_selectFunctional: function() {
				function v(t) {
					$("li.active", s).removeClass("active");
					$("li", s).eq(t).addClass("active");
					$("option[selected]", e).removeAttr("selected");
					u.eq(t).prop("selected", true);
					e.val(u.eq(t).val());
					n.text(u.eq(t).text());
					a = t;
					if(p) {
						e.change(u.eq(t).val());
						p(e.val())
					}
					return t
				}

				function m() {
					var e = 0,
						t = $(".select-items13", s);
					for(var n = 0, r = t.length; n < r; n++) {
						if($(t[n]).hasClass("active")) {
							break
						}
						e += $(t[n]).outerHeight()
					}
					s.stop().animate({
						scrollTop: e
					}, l)
				}
				if(this.element.is(":hidden")) {
					return false
				}
				var e = this.element,
					t = $("<div>").addClass(
						"select-main13"),
					n = $("<div>").addClass(
						"select-set13"),
					r = $("<div>").addClass(
						"select-arrow13"),
					i = $("<div>").addClass(
						"select-block13"),
					s = $("<ul>").addClass(
						"select-list13"),
					o = e.find("option[selected]").length,
					u = $(
						"option", e),
					a = 0,
					f = $("input[type=reset]",
						e.parents("form")),
					l = this.options.speed,
					c = this.options.keyControl,
					h = this.options.effect,
					p = this.options.change,
					d = this.options.scrollHeight;
				t.append(r);
				t.append(n);
				i.append(s);
				t.append(i);
				e.after(t);
				e.hide();
				i.hide();
				u.each(function(e, t) {
					s.append($("<li>").addClass("select-items13").text(
						$(t).text()))
				});
//				$(".select-items").click(function () {
//					alert($(this).text())
//				})
				
				f.click(function() {
					$(u, $(this).parents("form")).each(function(e, t) {
						if($(t).val() == "" && !$(t).is(":selected")) {
							$(".select-items13:first", s).click();
							return false
						}
					})
				});
				if(e.attr("disabled")) {
					t.addClass("disabled")
				}
				if(!o) {
					e.val(u.first().val());
					//							$("li:first", s).addClass("active");
					n.text(u.first().text())
				} else {
					e.val(e.find("option[selected]").val());
					$("li", s).eq(e.find("option[selected]").index())
						.addClass("active");
					n.text(e.find("option[selected]").text())
				}
				a = $("option[selected]", e).index();
				$("li", s).click(function() {
					var e = $(this).index();
					a = e;
					v(e)
				});
				if(d) {
					if(i.height() > d) {
						s.css("height", d).css("overflow", "auto")
					}
				}
				r.click(function() {
					n.click()
				});
				n
					.click(function() {
						var e = $(this);
						if(e.parent(t).hasClass("disabled")) {
							return false
						}
						if(!e.prev().hasClass("reverse")) {
							$(".select-arrow13").removeClass(
								"reverse");
							e.prev().addClass("reverse");
							e.parent().addClass("z-index")
						} else {
							e.parent().removeClass("z-index");
							e.prev().removeClass("reverse")
						}
						if(i.is(":hidden")) {
							if($(".select-block13").is(":visible")) {
								if(h == "slide") {
									$(".select-block13").slideUp(
										l / 2);
									$(".select-main13").removeClass("z-index")
								} else if(h == "fade") {
									$(".select-block13").fadeOut(l / 2);
									$(".select-main13").removeClass("z-index")
								}
								$(document).unbind("keydown")
							}
							$(document)
								.bind(
									"click",
									function(e) {
										if(!$(e.target)
											.is(
												".select-set13") &&
											!$(
												e.target)
											.is(
												".select-arrow13")) {
											if($(
													".select-block13")
												.is(
													":visible")) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main13")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow13")
														.removeClass(
															"reverse")
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main13")
																	.removeClass(
																		"z-index")
															});
													$(
															".select-arrow13")
														.removeClass(
															"reverse")
												}
												$(document)
													.unbind(
														"click keydown")
											}
										}
									});
							if(h == "slide") {
								i.slideDown(l);
								e.parent().addClass("z-index");
								m()
							} else if(h == "fade") {
								i.fadeIn(l);
								e.parent().addClass("z-index");
								m()
							}
							if(c) {
								$(document)
									.bind(
										"keydown",
										function(e) {
											var t = e.keyCode;
											if(t == 40) {
												if(a < u.length - 1) {
													a = a == -1 ? 0 :
														a;
													v(a += 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 38) {
												if(a > 0) {
													v(a -= 1);
													m()
												}
												e
													.preventDefault()
											}
											if(t == 13) {
												if(h == "slide") {
													i
														.slideUp(
															l / 2,
															function() {
																$(
																		".select-main13")
																	.removeClass(
																		"z-index")
															})
												} else if(h == "fade") {
													i
														.fadeOut(
															l / 2,
															function() {
																$(
																		".select-main13")
																	.removeClass(
																		"z-index")
															})
												}
												$(
														".select-arrow13")
													.removeClass(
														"reverse");
												$(document)
													.unbind(
														"keydown");
												e
													.preventDefault()
											}
										})
							}
						} else {
							if(h == "slide") {
								i.slideUp(l / 2, function() {
									$(".select-main13").removeClass(
										"z-index")
								});
								$(".select-arrow13").removeClass(
									"reverse")
							} else if(h == "fade") {
								i.fadeOut(l / 2, function() {
									$(".select-main13").removeClass(
										"z-index")
								});
								$(".select-arrow13").removeClass(
									"reverse")
							}
							$(document).unbind("keydown")
						}
					})
			}
		}
		);