create table food(
	foodName varchar2(20) primary key not null,
	foodType varchar2(5) not null
);

insert into food values('비빔밥','한식');
insert into food values('불고기','한식');
insert into food values('김치찌개','한식');
insert into food values('된장찌개','한식');
insert into food values('백반정식','한식');
insert into food values('갈비찜','한식');
insert into food values('미역국','한식');
insert into food values('소고기무국','한식');
insert into food values('낙지덮밥','한식');

insert into food values('자장면','중식');
insert into food values('짬뽕','중식');
insert into food values('탕수육','중식');
insert into food values('팔보채','중식');
insert into food values('볶음밥','중식');
insert into food values('짬짜면','중식');
insert into food values('군만두','중식');
insert into food values('유린기','중식');
insert into food values('누룽지탕','중식');

insert into food values('돈까스','양식');
insert into food values('토마토파스타','양식');
insert into food values('까르보나라','양식');
insert into food values('봉골레파스타','양식');
insert into food values('피자','양식');
insert into food values('샐러드','양식');
insert into food values('스테이크','양식');
insert into food values('함박스테이크','양식');
insert into food values('비프스튜','양식');