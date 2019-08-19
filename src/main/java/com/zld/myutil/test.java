package com.zld.myutil;

import com.zld.myutil.proxy.AllProxy;

public class test {
    public static void main(String[] args){

        //test1
/*        String value = "empno:123456|ename:老四|job:老板|salary:8000.50|hiredate:1997-04-29"
                +"|dept.dname:财务部|dept.company.name:阿里巴巴";
        Emp emp = ClassInstanceFactory.create(Emp.class, value);
        assert emp != null;
        System.out.println("编号："+emp.getEmpno()+"、姓名："+emp.getEname());
        System.out.println("职位："+emp.getJob()+"、薪水："+emp.getSalary()+"、入职时间："+emp.getHiredate());
        System.out.println(emp.getDept().getDname());
        System.out.println(emp.getDept().getCompany().getName());*/
        //-----------------------
        //test2
        IMessage msg = (IMessage) new AllProxy().bind(new MessageReal());
        msg.send();

    }
}
