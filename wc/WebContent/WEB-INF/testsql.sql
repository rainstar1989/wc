select timediff('2018-04-13 23:00:00','2018-04-12 15:00:00');
select left(timediff('2018-06-17 00:00:00','2018-06-16 23:00:01'),length(timediff('2018-06-17 00:00:00','2018-06-16 23:00:01'))-6);


/*过去24小时内比赛*/
select ev.evtime,ev.hteam,ev.gteam from worldcup2018.events ev where datediff('2018-06-25 23:00:00',ev.evtime) between 0 and 1;
/*未来24小时内比赛*/
select c.evid,c.evtime,a.tmname as '主队名称', b.tmname as '客队名称' from worldcup2018.events c,worldcup2018.teams a,worldcup2018.teams b where a.tmid=c.hteam and b.tmid=c.gteam and left(timediff(c.evtime,'2018-06-16 23:00:00'),length(timediff(c.evtime,'2018-06-16 23:00:00'))-6) between 0 and 24;



/*用户未猜的未来48小时内比赛*/
select c.evid,c.evtime,c.hteam,c.gteam,a.tmname as '主队名称', b.tmname as '客队名称' from worldcup2018.events c,worldcup2018.teams a,worldcup2018.teams b where a.tmid=c.hteam and b.tmid=c.gteam and left(timediff(c.evtime,'2018-06-16 23:00:00'),length(timediff(c.evtime,'2018-06-16 23:00:00'))-6) between 1 and 48 and c.evid not in (SELECT ub.evid FROM worldcup2018.userbetinfo ub where ub.uid='test2') order by c.evtime asc;
/*计算猜对场数*/
select count(ub.evid) from worldcup2018.userbetinfo ub,worldcup2018.events ev where ub.betresult is true and ub.uid='test2' and ub.evid=ev.evid and ev.evtype='groupmatch';