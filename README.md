#������

## README
������App��˷���������

###Database
ϵͳ���ݿ���ʱΪDerby��Derby��JDK�Դ����ݿ⣬��JDK��Ŀ¼��dbĿ¼�¡�
����bin\ij.bat(Windowsϵͳ), ʹ��Derby�������ݿ�ķ������£�

	ij> connect 'jdbc:derby:e:\roommates;create';
	ij> create table user_info (
			id int primary key,
			name varchar(30)
		);

###Requirement
	JDK1.7+
	Eclipse

###Using
  ����ʹ��git clone��Ŀ�����أ���ͨ��Eclipse������Ŀ��
  Maven���в�����clean compile tomcat:run

###Reference
- [Spring](http://spring.io/)
- [Mybatis](http://mybatis.github.io/)