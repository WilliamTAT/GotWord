use db_got_word;
select id from db_got_word.group where name = 'postman';
insert into word_has_group(word_word, word_group_id) values ('postman_1', 
	(select id from db_got_word.group where name = 'postman')
    );
    
select * from word_group;

select id from word_group where user_id = '1';

select word_word from word_has_group where word_group_id = 
		any(select id from word_group where user_id = '1');

select word from word where word = 
	any(select word_word from word_has_group where word_group_id = 
		any(select id from word_group where user_id = '1')
	);