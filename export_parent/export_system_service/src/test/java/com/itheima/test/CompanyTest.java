package com.itheima.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试企业的CRUD操作
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class CompanyTest {

//    @Autowired
//    private CompanyService companyService;
//
//    @Test
//    public void testFindAll(){
//        List<Company> companyList = companyService.findAll();
//        for(Company company : companyList){
//            System.out.println(company);
//        }
//    }
//
//    @Test
//    public void testSave(){
//        Company company = new Company();
//        company.setName("黑马ee107");
//        company.setAddress("广州市天河区");
//        company.setExpirationDate(new Date());
//        companyService.save(company);
//    }
}
