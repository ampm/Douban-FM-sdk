(function () {
    function y(R) {
        window.ga && window.ga.apply(null, R)
    }

    var K = "mouseenter mouseleave", p = $("#fm-channel-list"), o = $(".channel_list"), D = o.filter("#recent_chls"), w = o.filter("#promotion_chls"), e = o.filter("#fav_chls"), F = o.filter("#com_chls"), Q = o.filter("#history_chls"), l = $(".fm-sidectrl"), M = $(window), a = {}, P, H, E, d, L, u, A, N, c, b = 0, n = [0, -3, -8, -9];
    $(n).each(function () {
        var R = this;
        a[R] = function () {
            $(".channel[cid=" + R + "]").click()
        }
    });
    var r = function (R) {
        return $.inArray(R, n) != -1
    }, i = function () {
        if (!P) {
            P = $.template(null, $("#tmpl_chls").html())
        }
        return P
    }, C = function () {
        if (!d) {
            d = $.template(null, $("#tmpl_chl_tag").html())
        }
        return d
    }, j = function () {
        if (!u) {
            u = $.template(null, $("#tmpl_person_tag_maindialog").html())
        }
        return u
    }, x = function () {
        if (!A) {
            A = $.template(null, $("#tmpl_person_tags").html())
        }
        return A
    }, B = function () {
        if (!N) {
            N = $.template(null, $("#tmpl_fav_addable_chls").html())
        }
        return N
    }, z = function () {
        $("#promotion_chls").parent(".chl_section").before($.tmpl(getFavListTmpl(), null))
    }, h = function () {
        var S = $(".channel");
        var R = S.length;
        S.each(function (T) {
            $(this).css("z-index", (R - T))
        })
    }, t = function (S) {
        if (S.length) {
            S.hide();
            Q.prepend(S);
            S.fadeIn();
            var R = $("#history_chls .channel:last");
            if (R.hasClass("selected")) {
                $(".channel.selected, .fav_fast").fadeOut(function () {
                    D.append($(".fav_fast")).append(R);
                    $(".channel.selected, .fav_fast").fadeIn()
                })
            } else {
                R.fadeOut(function () {
                    R.remove()
                })
            }
        }
    }, g = function (R) {
        $("#sim-song").attr("ncid", R);
        $("#simulate-btn").hide();
        $("#simulate-hover").hide();
        $("#simulate-ready").fadeIn();
        $('.channel[cid="' + R + '"]').trigger("click")
    }, k = function (R) {
        $.getJSON("/j/explore/get_channel_info?cid=" + R, function (S) {
            if (S.status) {
                var T = S.data.res;
                if (!T.hasOwnProperty("id")) {
                    return
                }
                $(".fav_fast").hide();
                t($("#recent_chls .channel.selected"));
                var U = $.tmpl(B(), {chls: [
                    {id: T.id, cover: T.cover, name: T.name, intro: T.intro}
                ], exist_chl_num: e.find("li.channel").length, is_fav: false}).hide();
                $("#recent_chls").append(U);
                h();
                U.fadeIn(function () {
                    g(R)
                })
            }
        })
    }, v = function () {
        var R = $.map($(".chl_section #promotion_chls li.channel, .chl_section #history_chls li.channel"), function (S) {
            return $(S).attr("cid")
        }).join("|");
        $.getJSON("/j/explore/get_recommend_chl?orecs=" + R, function (S) {
            if (S.status) {
                var T = S.data.res;
                if (!T.hasOwnProperty("id")) {
                    return
                }
                var U = $.tmpl(B(), {chls: [
                    {id: T.id, cover: T.cover, name: T.name, intro: T.intro}
                ], exist_chl_num: e.find("li.channel").length, is_fav: false}).hide();
                w.append(U);
                h();
                U.fadeIn()
            }
        })
    }, G = function () {
        $("#faved").text(parseInt($("#faved").text(), 10) - 1);
        var R = $.map($(".chl_section #fav_chls li.channel, .chl_section #history_chls li.channel"), function (S) {
            return $(S).attr("cid")
        }).join("|");
        $.getJSON("/j/explore/get_fav_chl?ofavs=" + R, function (S) {
            var T = S.data.res;
            if (!T.hasOwnProperty("id")) {
                return
            }
            var U = $.tmpl(B(), {chls: [
                {id: T.id, cover: T.cover, name: T.name, intro: T.intro}
            ], exist_chl_num: e.find("li.channel").length, is_fav: true}).hide();
            e.append(U);
            h();
            U.fadeIn()
        })
    }, I = function (W) {
        var V = $(".channel[cid=" + W + "] .menu .fav"), S = $("#fav-channel-btn[cid=" + W + "]"), R = $(".channel[cid=" + W + "]"), U = $(".fav_fast[cid=" + W + "]"), T = $(".channel[cid=" + W + "], .fav_fast[cid=" + W + "]");
        T.fadeOut(function (X) {
            if ($(this).hasClass("channel")) {
                V.text("取消收藏").removeClass("fav").addClass("unfav");
                S.text("取消收藏").removeClass("fav-channel-btn").addClass("unfav-channel-btn");
                U.removeClass("unfav_shape").addClass("fav_shape");
                e.parent(".chl_section").show();
                var Y = !!$("#promotion_chls .channel[cid=" + W + "]").length;
                $("#fav_chls").prepend(R).prepend(U);
                T.fadeIn(function (Z) {
                    if ($(this).hasClass("channel")) {
                        h();
                        $("#user_play_record a #faved").text(parseInt($("#user_play_record a #faved").text()) + 1);
                        if (Y) {
                            v()
                        }
                        if ($("#fav_chls .channel").length > 5) {
                            $("#fav_chls .channel:last").remove()
                        }
                        $("#fm-channel-list").scrollTop($("#system_chls").outerHeight() + 51)
                    }
                })
            }
        })
    }, m = function (W) {
        var V = $(".channel[cid=" + W + "] .menu .unfav"), S = $("#fav-channel-btn[cid=" + W + "]"), R = $(".channel[cid=" + W + "]"), U = $(".fav_fast[cid=" + W + "]"), T = $(".channel[cid=" + W + "], .fav_fast[cid=" + W + "]");
        S.text("收藏").removeClass("unfav-channel-btn").addClass("fav-channel-btn");
        T.fadeOut(function (X) {
            if ($(this).hasClass("channel")) {
                if (H == W) {
                    t($("#recent_chls .channel.selected"));
                    $("#recent_chls").append(U).append(R);
                    U.removeClass("fav_shape").addClass("unfav_shape");
                    V.text("收藏").removeClass("unfav unfav_del").addClass("fav");
                    h()
                } else {
                    removeChannelElement(V.parent(".menu").parent(".channel"));
                    U.remove();
                    t($(".channel[cid=" + W + "]"));
                    G();
                    return
                }
                T.fadeIn(function (Y) {
                    if ($(this).hasClass("channel")) {
                        G();
                        if (!$("#fav_chls .channel").length) {
                            e.parent(".chl_section").hide()
                        }
                    }
                })
            }
        })
    }, O = function () {
        $(".tag-input-form .tag-input-inner").iSuggest({api: "/j/person_tag_suggest", tmplId: "tag_sugg_result", item_act: function (V, T, R) {
            var S = $(".tag-input-form .tag-input-inner").val();
            if (S != "") {
                $(".warning_info div").hide();
                if ($(".tags-to-create .tag-show-div .tag-show-span").length > 2) {
                    $(".warning_info .person_limited_tags").show();
                    return
                }
                var U = $(".tag-show-div").width();
                $(".tag-input-form .tag-input-inner").width(1);
                $(".tag-show-div").append('<span class="tag-show-span" id="' + V + '" type="' + R + '"><span class="del">x</span><span class="val">' + T + "</span></span>");
                if ($(".tag-show-div").width() > U) {
                    $(".tag-show-div").width($(".tag-show-div").width() + 1)
                } else {
                    $(".tag-show-div").width(U + $(".tag-show-span:last").outerWidth(true) + 1)
                }
                if ($(".tag-input-form").width() - $(".tag-show-div").outerWidth(true) < 0) {
                    $(".tag-input-form .tag-input-inner").width(0)
                } else {
                    $(".tag-input-form .tag-input-inner").width($(".tag-input-form").width() - $(".tag-show-div").outerWidth(true))
                }
                $(".tag-input-form .tag-input-inner").val("");
                $(".tag-show-div .tag-show-span").removeClass("pre_del");
                $(".tag-input-form .tag-input-inner").focus();
                $(".warning_info div").hide()
            }
        }});
        $(".tag-show-div").delegate(".del", "click", function (R) {
            $(".tag-show-div").width(Math.max(0, $(".tag-show-div").width() - $(this).parent(".tag-show-span").outerWidth(true) - 1));
            $(this).parent(".tag-show-span").remove();
            $(".tag-input-form .tag-input-inner").width($(".tag-input-form").width() - $(".tag-show-div").outerWidth(true));
            $(".warning_info div").hide();
            $(".tag-input-inner").focus()
        });
        $(".alt_tags .tag_div").delegate(".tags", "click", function (T) {
            var R = $(this);
            $tag = R.text();
            var S = $(".tag-show-div .tag-show-span").length;
            $(".warning_info div").hide();
            if (S < 3) {
                if ($(".tag-show-div .tag-show-span[id='" + R.attr("id") + "']").length) {
                    $(".warning_info div").hide();
                    $(".warning_info .person_tag_repeat").show();
                    return
                }
                var U = $(".tag-show-div").width();
                $(".tag-input-form .tag-input-inner").width(1);
                $(".tag-show-div").append('<span class="tag-show-span" id="' + R.attr("id") + '" type="' + R.attr("type") + '"><span class="del">x</span><span class="val">' + $tag + "</span></span>");
                if ($(".tag-show-div").width() > U) {
                    $(".tag-show-div").width($(".tag-show-div").width() + 1)
                } else {
                    $(".tag-show-div").width(U + $(".tag-show-span:last").outerWidth(true) + 1)
                }
                if ($(".tag-input-form").width() - $(".tag-show-div").outerWidth(true) < 0) {
                    $(".tag-input-form .tag-input-inner").width(0)
                } else {
                    $(".tag-input-form .tag-input-inner").width($(".tag-input-form").width() - $(".tag-show-div").outerWidth(true))
                }
                $(".tag-show-div .tag-show-span").removeClass("pre_del");
                $(".tag-input-form .tag-input-inner").focus()
            } else {
                $(".warning_info .person_limited_tags").show()
            }
        });
        $(".tmpl_person_tag_maindialog").delegate(".direct_start", "click", function (R) {
            cid = -9;
            DBR.close_video().act("switch", 0);
            H = cid;
            markPlayingChannelClass(cid, $('.channel[cid="' + cid + '"]'));
            $(".tmpl_person_tag_maindialog").hide();
            $(".overlay").remove()
        }).delegate(".start_private_tag", "click", function (T) {
            if ($(".tag-show-div .tag-show-span").length == 0 && $(".tag-input-form .tag-input-inner").val()) {
                $(".warning_info div").hide();
                $(".warning_info .person_tag_not_exist").show();
                return
            }
            cid = -9;
            var R = "";
            var S = "";
            $(".tag-show-div .tag-show-span").each(function () {
                R += $(this).attr("id") + "_";
                S += $(this).children(".val").text() + "_"
            });
            if (R) {
                R = R.substring(0, R.length - 1);
                S = S.substring(0, S.length - 1);
                DBR.close_video().act("switch", cid, R)
            } else {
                DBR.close_video().act("switch", 0)
            }
            H = cid;
            markPlayingChannelClass(cid, $('.channel[cid="' + cid + '"]'));
            $(".tmpl_person_tag_maindialog").hide();
            $(".overlay").remove()
        });
        $(".tag-input-inner").keydown(function (S) {
            if (S.keyCode == 8 && $(".tag-input-inner").val() == "") {
                var R = $(".tag-show-div .tag-show-span:last");
                if (R) {
                    if (R.hasClass("pre_del")) {
                        $(".tag-show-div").width(Math.max(0, $(".tag-show-div").width() - R.outerWidth(true) - 1));
                        R.remove();
                        $(".tag-input-form .tag-input-inner").css("width", ($(".tag-input-form").width() - $(".tag-show-div").outerWidth(true)));
                        $(".warning_info div").hide();
                        $(".tag-input-inner").focus()
                    } else {
                        R.addClass("pre_del")
                    }
                }
            }
        })
    }, J = function () {
        $.getJSON("/j/explore/person_channnel_tags", function (V) {
            var T = "";
            if (u) {
                $(".tmpl_person_tag_maindialog").show()
            } else {
                $("body").append($.tmpl(j(), null));
                O()
            }
            if (V.status) {
                var R = V.data.res;
                var T = "[";
                var U = 0, S = 0;
                for (; R.artist[U] || R.genre[S];) {
                    if (U + S > 0) {
                        T += ","
                    }
                    if (Math.random() > 0.5 && R.genre[S]) {
                        T += JSON.stringify(R.genre[S]);
                        ++S
                    } else {
                        if (Math.random() <= 0.5 && R.artist[U]) {
                            T += JSON.stringify(R.artist[U]);
                            ++U
                        } else {
                            if (R.artist[U]) {
                                T += JSON.stringify(R.artist[U]);
                                ++U
                            } else {
                                if (R.genre[S]) {
                                    T += JSON.stringify(R.genre[S]);
                                    ++S
                                }
                            }
                        }
                    }
                }
                T += "]"
            }
            $(".tmpl_person_tag_maindialog .alt_tags .tag_div .tags").remove();
            $(".tmpl_person_tag_maindialog .alt_tags .tag_div").append($.tmpl(x(), $.parseJSON(T)));
            $(".tag-input-inner").placeholder();
            $("body").append($('<div class="overlay" style="display:block;background:#eef1f0;opacity:0.5;"></div>'));
            $(".tag-show-div .tag-show-span").remove();
            $(".tag-show-div").attr("style", "");
            var X = get_cookie("person_tags");
            var W = get_cookie("person_tag_names");
            if (X && W) {
                X = X.split("_");
                W = W.split("_");
                var U = 0;
                for (; U < X.length && U < W.length && U < 3; ++U) {
                    if (X[U] == "" || W[U] == "") {
                        break
                    }
                    $(".tag-show-div").append('<span class="tag-show-span" id="' + X[U] + '"><span class="del">x</span><span class="val">' + W[U] + "</span></span>")
                }
                $(".tag-input-form .tag-input-inner").width(1);
                $(".tag-show-div").width($(".tag-show-div").width() + 1 * U);
                if ($(".tag-input-form").width() - $(".tag-show-div").outerWidth(true) < 0) {
                    $(".tag-input-form .tag-input-inner").width(0)
                } else {
                    $(".tag-input-form .tag-input-inner").width($(".tag-input-form").width() - $(".tag-show-div").outerWidth(true))
                }
                $(".tag-input-form .tag-input-inner").focus()
            }
        })
    }, q = function () {
        $(".tmpl_chl_tag").delegate(".tag_unselected", "click", function (T) {
            var S = $(this);
            var R = S.closest("div").children(".tag_selected").length;
            if (R < 3) {
                S.removeClass().addClass("tag_selected")
            } else {
                $("#nosong_found").hide();
                $("#warning_limited_tag").show()
            }
            if ($(".start_tag_func").hasClass("disabled")) {
                $(".start_tag_func").removeClass("disabled")
            }
        }).delegate(".tag_selected", "click", function (S) {
            this.className = "tag_unselected";
            var R = $(this).closest("div").children(".tag_selected").length;
            if (!R) {
                $(".start_tag_func").addClass("disabled")
            }
            if (R < 3 && $("#warning_limited_tag").css("display") != "hidden") {
                $("#warning_limited_tag").hide()
            }
        }).delegate("#start_tag_func", "click", function (T) {
            var S = $(this);
            var R = S.closest(".tmpl_chl_tag").find(".tag_selected").length;
            if (!R) {
                return
            }
            var V = -8;
            if (user_record.num.liked === 0) {
                return
            }
            var U = $("#tags_div .tag_selected").map(function () {
                return $(this).attr("id")
            }).get().join("_");
            if (!U.length) {
                return
            }
            $.getJSON("/j/explore/is_tags_song_exist?tags=" + U, function (X) {
                if (X.status) {
                    if (X.data.exist > 0) {
                        var Y = "";
                        for (var W = 0; W < R; ++W) {
                            Y += $("#tags_div .tag_selected:eq(" + W + ")").attr("id") + "_"
                        }
                        Y = Y.substr(0, Y.length - 1);
                        DBR.close_video().act("switch", V, Y);
                        H = V;
                        markPlayingChannelClass(V, $('.channel[cid="' + V + '"]'));
                        $(".tmpl_chl_tag").fadeOut("fast", function () {
                            $(".tmpl_chl_tag").remove()
                        });
                        $(".overlay").remove();
                        set_cookie({red_tags: Y}, 365, "douban.fm")
                    } else {
                        $("#warning_limited_tag").hide();
                        $("#nosong_found").show()
                    }
                } else {
                    alert(X.msg)
                }
            })
        }).delegate("#cancel_tag_func", "click", function (R) {
            $(".tmpl_chl_tag").fadeOut("fast", function () {
                $(".tmpl_chl_tag").remove()
            });
            $(".overlay").remove()
        })
    }, s = function () {
        $(".fm-app-tip").delegate(".cancel", "click", function (R) {
            $("#fm-app-tip").fadeOut("normal", function (S) {
                $("#login-tip").fadeOut("normal", function (T) {
                    $("#login-tip").fadeIn();
                    f()
                })
            })
        })
    }, f = function () {
        $(".login-tip").delegate(".cancel", "click", function (R) {
            $("#login-tip").fadeOut("normal")
        })
    };
    regGuideEventBind = function () {
        if ($("#reg-guide").length) {
            $("#reg-guide").delegate(".artist", "click", function (R) {
                var S = 0;
                H = S;
                DBR.close_video().act("switch", S, "", $(this).data("artist-id"));
                markPlayingChannelClass(S, $('.channel[cid="' + S + '"]'))
            })
        }
    }, removeChannelElement = function (R) {
        R.fadeOut(function () {
            if (R.parent("#promotion_chls").length) {
                v()
            }
            var T = R.data("cid");
            var S = R.next(".channel");
            if (!S.length) {
                S = R.prev(".channel")
            }
            R.remove();
            if (T === H) {
                if (!S.length) {
                    S = $("#system_chls li:first")
                }
                if (!S.length) {
                    S = $("#ss-intros .ss-intro:first h3")
                }
                if (S.length) {
                    S.click()
                }
            }
        })
    }, markPlayingChannelClass = function (S, R) {
        $("li.selected").removeClass("selected");
        if (!R) {
            R = $(".channel[cid=" + S + "]:first")
        }
        R.addClass("selected");
        if (r(S)) {
            R.removeClass("hover")
        }
        $(".channel .menu .fav").hide();
        $(".channel[cid=" + S + "] .menu .fav").show()
    };
    M.bind(window.consts.LOGIN_EVENT, function (S, R) {
        $.getJSON("/j/explore/get_login_chls?uk=" + R.id, function (V) {
            if (V.status) {
                var Y = $(".channel[cid=" + H + "]");
                var Z = Y.attr("data-cover");
                var X = Y.attr("data-intro");
                var U = $(".channel[cid=" + H + "] .chl_name").text();
                U = U.substring(0, U.indexOf(" M"));
                $("#promotion_chls").html($.tmpl(B(), {chls: V.data.res.rec_chls, exist_chl_num: 0, is_fav: false}));
                if (V.data.res.fav_chls) {
                    $("#fav_list").parent(".chl_section").show()
                }
                $("#fav_chls").html($.tmpl(B(), {chls: V.data.res.fav_chls, exist_chl_num: 0, is_fav: true}));
                h();
                if ($(".channel[cid=" + H + "]").length > 1) {
                    roundHisChannel($("#recent_chls .channel[cid=" + H + "]"));
                    $("#recent_chls .fav_fast[cid=" + H + "]").remove()
                }
                if (!U) {
                    $(".channel:first").click();
                    return
                }
                if (!$(".channel[cid=" + H + "]").length) {
                    $.getJSON("/j/explore/is_fav_channel?uk=" + R.id + "&cid=" + H, function (ab) {
                        D.html($.tmpl(B(), {chls: [
                            {id: H, cover: Z, name: U, intro: X}
                        ], exist_chl_num: 0, is_fav: ab.data.res.is_fav}));
                        markPlayingChannelClass(H, $(".channel[cid=" + H + "]"));
                        var aa = $(".channel[cid=" + H + "]");
                        if (ab.data.res.is_fav) {
                            aa.before("<span cid='" + H + "' class='fav_fast fav_shape'><span class='star'></span></span>")
                        } else {
                            aa.before("<span cid='" + H + "' class='fav_fast unfav_shape'><span class='star'></span></span>")
                        }
                        h()
                    })
                } else {
                    markPlayingChannelClass(H, $(".channel[cid=" + H + "]"));
                    var W = $(".fav_fast[cid=" + H + "]");
                    var T = $(".channel[cid=" + H + "]");
                    if (!W.length) {
                        if ($("#fav_chls .channel[cid=" + H + "]").length) {
                            T.before("<span cid='" + H + "' class='fav_fast fav_shape'><span class='star'></span></span>")
                        } else {
                            T.before("<span cid='" + H + "' class='fav_fast unfav_shape'><span class='star'></span></span>")
                        }
                    }
                    h()
                }
            } else {
                alert(V.msg)
            }
        })
    }).bind("channel:switch", function (V, T) {
        var S = o.find(".channel[cid=" + T.id + "]"), U, R;
        if (!S.length) {
            U = $.tmpl(i(), {chls: [
                {id: T.id, cover: T.cover, name: T.name, intro: T.intro, com_len: $("#com_songs_sec .ch_item[data-cid=" + T.id + "]").length}
            ], exist_chl_num: D.find("li.channel").length + w.find("li.channel").length + e.find("li.channel").length + F.find("li.channel").length});
            if ($("#com_songs_sec ul li[data-cid=" + T.id + "]").length) {
                S = U.hide().prependTo(F).fadeIn(200)
            } else {
                if ($("#fm-channel-list[type=fav_func]").length) {
                    D.find("li").remove()
                }
                S = U.hide().prependTo(D).fadeIn(200)
            }
        }
        S.click();
        R = S.position().top - w.position().top;
        p.stop().animate({scrollTop: R}, 300)
    }).bind("channel:fav", function (S, T, R) {
        if (R) {
            I(T)
        } else {
            m(T)
        }
    }).bind("channel:favAddhide", function (V, T) {
        var S = o.find(".channel[cid=" + T.id + "]"), U, R;
        if (!S.length) {
            U = $.tmpl(B(), {chls: [
                {id: T.id, cover: T.cover, name: T.name, intro: T.intro}
            ], exist_chl_num: D.find("li.channel").length + w.find("li.channel").length + e.find("li.channel").length + F.find("li.channel").length, is_fav: true});
            U.appendTo("body").hide()
        }
    });
    o.delegate(".channel", K, function (T) {
        var R = $(this);
        var U = R.hasClass("personal");
        if (U && R.hasClass("selected")) {
            return
        }
        if (R.data("cid") === -3 && user_record.num.liked === 0) {
            var S = $("#red_chl_tip");
            if (T.type === "mouseenter") {
                S.css({left: R.position().left + R.width() + 10, top: R.position().top}).fadeIn("fast")
            } else {
                S.fadeOut("fast")
            }
        }
        if (T.type === "mouseenter") {
            R.addClass("hover")
        } else {
            R.removeClass("hover")
        }
    });
    $("body").append($("#red_chl_tip"));
    o.delegate('.channel[cid="-8"]', "click", function (R) {
        R.stopPropagation();
        R.preventDefault();
        if ($(".tmpl_chl_tag").length || !o.children(".channel.selected").length) {
            return
        }
        $.getJSON("/j/explore/channel_personnel_tag", function (V) {
            if (V.status) {
                $("body").append($.tmpl(C(), V.data));
                q();
                $("body").append($('<div class="overlay" style="display:block;background:#eef1f0;opacity:0.5;"></div>'));
                var S = get_cookie("red_tags");
                if (S) {
                    tag_list = S.split("_");
                    for (var U = 0; U < tag_list.length; ++U) {
                        var T = $(".tmpl_chl_tag #tags_div span[id='" + tag_list[U] + "']");
                        if (T.length) {
                            T.addClass("tag_selected")
                        }
                    }
                }
                if ($(".tmpl_chl_tag .tag_selected").length > 0) {
                    $(".start_tag_func").removeClass("disabled")
                }
            } else {
                alert(V.msg)
            }
        })
    });
    o.delegate('.channel[cid="-9"]', "click", function (S) {
        S.stopPropagation();
        S.preventDefault();
        if ($('.channel[cid="-9"]').attr("status") == 1) {
            var R = $("#user_name").text().replace(/(^\s*)|(\s*$)/g, "") || "Hi";
            $("body").append($('<div class="overlay" style="display:block;background:#eef1f0;opacity:0.5;"></div>'))
        } else {
            J()
        }
    });
    o.delegate(".channel:not(.selected)", "click", function (V) {
        var T = $(this), R = $(".channel.selected"), X = T.data("cid"), W = r(X), U, S;
        if (X === -3 && user_record.num.liked === 0) {
            return
        }
        if (X === -8 || X === -9) {
            if (!o.children(".channel.selected").length) {
                H = X;
                markPlayingChannelClass(X, T);
                $(".fav_fast").remove()
            }
            return
        }
        $(".fav_fast").remove();
        if ($("#recent_chls .channel[cid=" + X + "]").length && !$(".fav_fast[cid=" + X + "]").length) {
            $.getJSON("/j/explore/is_fav_channel?cid=" + X, function (Z) {
                var Y = $(".channel[cid=" + X + "]");
                if (Z.data.res.is_fav) {
                    Y.before("<span cid='" + X + "' class='fav_fast fav_shape'><span class='star'></span></span>")
                } else {
                    Y.before("<span cid='" + X + "' class='fav_fast unfav_shape'><span class='star'></span></span>")
                }
            })
        } else {
            if ($.inArray(X, n) == -1 && !$("#com_chls .channel[cid=" + X + "]").length) {
                if (T.find(".menu .fav").length && !$(".fav_fast[cid=" + X + "]").length) {
                    T.before("<span cid='" + X + "' class='fav_fast unfav_shape'><span class='star'></span></span>")
                } else {
                    T.before("<span cid='" + X + "' class='fav_fast fav_shape'><span class='star'></span></span>")
                }
            }
        }
        if ($("#sim-song").attr("ncid") > 0) {
            DBR.close_video().act("switch", X, "", "", true);
            $("#sim-song").attr("ncid", 0)
        } else {
            DBR.close_video().act("switch", X);
            $("#simulate-ready").hide();
            $("#simulate-btn").fadeIn()
        }
        H = X;
        markPlayingChannelClass(X, T);
        if (!$("#recent_chls .channel[cid=" + X + "]").length) {
            t($("#recent_chls .channel:first"))
        }
        if ($("#recent_chls .channel").length > 1) {
            t($("#recent_chls .channel:last"))
        }
        if (X !== -8) {
            $(".tmpl_chl_tag").fadeOut("fast", function () {
                $(".tmpl_chl_tag").remove()
            })
        }
        S = T.find(".chl_name").text();
        U = T.closest(".channel_list").attr("id");
        if ($("#simulate-sec").attr("click")) {
            U = "songchannel";
            $("#simulate-sec").removeAttr("click")
        } else {
            if ($("ul.fm-djlist[data-cate=search_result] li.hover[data-cid=" + X + "]").length) {
                U = "search"
            }
        }
        $.getJSON("/j/change_channel?fcid=" + R.data("cid") + "&tcid=" + X + "&area=" + U);
        if (U != "recent_chls") {
            y(["send", "event", U, "clicked", S, T.index() + 1])
        }
    });
    initAutoHideMenu(".channel .toggle", ".channel .menu", {root: o, getMenu: function (R) {
        return R.closest(".channel").find(".menu")
    }, getQuickMenu: function (R) {
        return $(".fav_fast[cid=" + R.closest(".channel").attr("cid") + "]")
    }});
    o.delegate(".channel .menu li", K, function (S) {
        var R = $(this);
        if (S.type === "mouseenter") {
            R.addClass("hover")
        } else {
            R.removeClass("hover")
        }
    }).delegate(".channel .menu .del", "click", function (W) {
        W.stopPropagation();
        var T = $(this).closest(".channel"), X = T.data("cid"), S = T.find(".chl_name").text(), R = T.closest(".channel_list"), V, U;
        if (R.is("#fav_chls")) {
            U = "Fav Channel Del"
        } else {
            U = "Promo Channel Del"
        }
        if (U) {
            y(["send", "event", U, "clicked", S])
        }
        if (R.is("#promotion_chls")) {
            removeChannelElement(T);
            return
        }
        if (R.is("#recent_chls")) {
            V = "/j/explore/rm_recent_chl"
        } else {
            return
        }
        $.post_withck(V, {cid: X}, function (Y) {
            if (Y.status) {
                removeChannelElement(T)
            } else {
                alert(Y.msg)
            }
        }, "json")
    }).delegate(".channel .menu .share", "click", function (Z) {
        Z.stopPropagation();
        Z.preventDefault();
        var S = $(this).closest(".channel"), aa = S.data("cid"), V = S.data("cover"), ab = S.data("intro"), T = S.find(".chl_name").text(), W = window.consts.FM_URL + "?cid=" + aa, U;
        if (S.closest(".channel_list").is("#fav_chls")) {
            U = "Fav Channel Share"
        } else {
            U = "Promo Channel Share"
        }
        if (U) {
            y(["send", "event", U, "clicked", T])
        }
        var X = {name: T, href: W, image: V, desc: ab, apikey: "079055d6a0d5ddf816b10183e28199e8", target_type: "rec", target_action: 0, object_kind: window.consts.CHL_REC_KIND, object_id: aa, action_props: JSON.stringify({channel_url: W, channel_title: T + " - 豆瓣FM"})};
        var R = "http://www.douban.com/share/recommend?" + $.param(X);
        var Y = window.open(R, "fm", ["toolbar=0,status=0,resizable=1,width=500,height=360,left=", (screen.width - 500) / 2, ",top=", (screen.height - 360) / 2].join(""));
        if (Y) {
            Y.focus()
        }
    }).delegate(".channel .menu .show-channel-detail", "click", function (S) {
        S.stopPropagation();
        S.preventDefault();
        l.click();
        var R = $(this).closest(".channel"), T = R.data("cid");
        render_channel_detail("/j/explore/channel_detail?channel_id=" + T, "side-channel-detail-template", "home")
    }).delegate(".channel .menu .fav", "click", function (S) {
        S.stopPropagation();
        S.preventDefault();
        $el = $(this);
        var R = $(this).closest(".channel"), T = R.data("cid");
        $.getJSON("/j/explore/fav_channel?cid=" + T, function (U) {
            if (U.status) {
                I(T)
            } else {
                alert(U.msg)
            }
        })
    }).delegate(".channel .menu .unfav", "click", function (S) {
        S.stopPropagation();
        S.preventDefault();
        $el = $(this);
        var R = $(this).closest(".channel"), T = R.data("cid");
        $.getJSON("/j/explore/unfav_channel?cid=" + T, function (U) {
            if (U.status) {
                m(T)
            } else {
                alert(U.msg)
            }
        })
    }).delegate(".fav_fast", "click", function (S) {
        S.stopPropagation();
        S.preventDefault();
        $el = $(this);
        var T = $el.attr("cid");
        var R = "/j/explore/fav_channel?cid=";
        if ($(".fav_fast[cid=" + T + "]").hasClass("fav_shape")) {
            R = "/j/explore/unfav_channel?cid="
        }
        $.getJSON(R + T, function (U) {
            if (U.status) {
                if (R.indexOf("/j/explore/fav_channel?cid=") >= 0) {
                    I(T)
                } else {
                    m(T)
                }
            } else {
                alert(U.msg)
            }
        })
    });
    D.find("li").click(function (S, T) {
        if (T) {
            return
        }
        var R = $(this);
        if (R.hasClass("selected")) {
            return
        }
        $.post_withck("/j/channel_select", {cid: R.data("cid"), ctype: "r"})
    });
    e.find("li").click(function (S, T) {
        if (T) {
            return
        }
        var R = $(this);
        if (R.hasClass("selected")) {
            return
        }
        $.post_withck("/j/channel_select", {cid: R.data("cid"), ctype: "c"})
    });
    regGuideEventBind();
    $("#simulate-sec .wrapper").click(function (S, V) {
        var R = $(this).parent("#simulate-sec");
        var T = Number(R.attr("schid"));
        if (T) {
            if (T == H) {
                return
            }
            var U = $('.channel[cid="' + T + '"]');
            H = T;
            R.attr("click", "1");
            if (!U.length) {
                k(T)
            } else {
                g(T)
            }
        }
    }).mouseenter(function (R) {
        if ($("#simulate-btn").css("display") == "none") {
            return
        }
        $("#simulate-btn").hide();
        $("#simulate-hover").css({"background-color": "#e1e8e5"}).show().animate({"background-color": "#9cd6c5"}, 150, "linear")
    }).mouseleave(function (R) {
        if ($("#simulate-hover").css("display") == "none") {
            return
        }
        $("#simulate-hover").hide();
        $("#simulate-btn").css({"background-color": "#9cd6c5"}).show().animate({"background-color": "#e1e8e5"}, 150, "linear")
    });
    $("#red-his-btn").click(function () {
        set_cookie({sawred: 1}, 365, "douban.fm")
    });
    $("#simulate-sec").delegate("#sim-song", "click", function (R) {
        if (DBR.is_paused()) {
            DBR.act("pause")
        }
        DBR.close_video().act("skip");
        $("#simulate-ready").hide();
        $("#simulate-btn").fadeIn()
    })
})();