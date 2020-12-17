package com.cycas.service.impl;

import com.cycas.dao.SyncSysRoleFuncDAO;
import com.cycas.dao.UserDao;
import com.cycas.dao.WorkTypeDao;
import com.cycas.entity.BaseServiceWorkType;
import com.cycas.entity.SyncSysRoleFuncDTO;
import com.cycas.service.WorkTypeService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author naxin
 * @Description:
 * @date 2020/12/910:41
 */
@Service
public class WorkTypeServiceImpl implements WorkTypeService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private WorkTypeDao workTypeDao;
    @Autowired
    private SyncSysRoleFuncDAO syncSysRoleFuncDAO;

    @Override
    public int insert(Integer i) {
        BaseServiceWorkType dept = new BaseServiceWorkType();
        dept.setCompanyId(7011);
        dept.setName("naxin");
        dept.setCode("code");
        dept.setSystemFlag(i + "");
        dept.setStatusCode(i + "");
        dept.setSeq(0);
        dept.setCreater("cyc");
        dept.setCreateTime(new Date());
        dept.setDeleteFlag("N");
        workTypeDao.insert(dept);
        if (i == 1) {
            throw new RuntimeException();
        }
        return 0;
    }

    @Override
    public int batchSaveWorkTypes() {

        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();
//            sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            WorkTypeDao workTypeMapper = sqlSession.getMapper(WorkTypeDao.class);

            long start =System.currentTimeMillis();
            List<BaseServiceWorkType> deptList=new ArrayList<>();
            for (int i = 0; i <10000 ; i++) {
                BaseServiceWorkType dept = new BaseServiceWorkType();
                dept.setCompanyId(7011);
                dept.setName("name" + i);
                dept.setCode("code" + i);
                dept.setSystemFlag("i");
                dept.setStatusCode("status" + i);
                dept.setSeq(i);
                dept.setCreater("cyc");
                dept.setCreateTime(new Date());
                dept.setDeleteFlag("N");
                deptList.add(dept);
                if(i%100 == 0){
                    workTypeMapper.insertBatch(deptList);
                    deptList.clear();
                }
            }
            workTypeMapper.insertBatch(deptList);
            long end =System.currentTimeMillis();
            System.out.println("耗时:"+(end-start));
            // 500条一批次，耗时：6.106s 开启BATCH：7.293s
            // 100条一批次，耗时：3.908s 开启BATCH：2.378s
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
            logger.error("BaseServiceWorkTypeServiceImpl batchUpdateWorkTypes error{}", e);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return 0;
    }

    @Override
    public int batchUpdateWorkTypes() {

        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            WorkTypeDao workTypeMapper = sqlSession.getMapper(WorkTypeDao.class);

            long start =System.currentTimeMillis();
            List<BaseServiceWorkType> deptList=new ArrayList<>();
            for (int i = 1; i <10000 ; i++) {
                BaseServiceWorkType dept = new BaseServiceWorkType();
                dept.setId(i);
                dept.setCompanyId(3401);
                dept.setName("name" + i);
                dept.setCode("code");
                dept.setSystemFlag("i");
                dept.setStatusCode("status");
                dept.setSeq(i);
                dept.setCreater("cyc");
                dept.setCreateTime(new Date());
                dept.setUpdater("cyc");
                dept.setUpdateTime(new Date());
                dept.setDeleteFlag("N");
                deptList.add(dept);
                if(i%100 == 0){
                    workTypeMapper.updateBatch(deptList);
                    deptList.clear();
                }
            }
            if (deptList.size() > 0) {
                workTypeMapper.updateBatch(deptList);
            }
            long end =System.currentTimeMillis();
            System.out.println("耗时:"+(end-start));
            // 500条一批次，耗时：34.316s 开启BATCH：2.327
            // 100条一批次，耗时：38.102s 开启BATCH：4.635
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
            logger.error("BaseServiceWorkTypeServiceImpl batchUpdateWorkTypes error{}", e);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return 0;
    }

    @Override
    @Transactional
    public int batchSaveRoleFunc() {

//        SqlSession sqlSession = null;
//        try {
//            sqlSession = sqlSessionFactory.openSession();
//            sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
//            WorkTypeDao workTypeMapper = sqlSession.getMapper(WorkTypeDao.class);

            long start = System.currentTimeMillis();
            List<SyncSysRoleFuncDTO> list = new ArrayList<>();
            Date currentDate = new Date();
            for (int i = 1; i < 100000; i++) {
                SyncSysRoleFuncDTO dept = new SyncSysRoleFuncDTO();
                dept.settSysFuncId(i);
                dept.settSysRoleId(i);
                dept.setCreateTime(currentDate);
                dept.setUpdateTime(currentDate);
                list.add(dept);
                if (i % 500 == 0) {
                    syncSysRoleFuncDAO.insertBatch(list);
                    list.clear();
                }
            }
            syncSysRoleFuncDAO.insertBatch(list);
            long end = System.currentTimeMillis();
            logger.info("耗时:" + (end - start));
            // 500条一批次，耗时：6.106s 开启BATCH：7.293s
            // 100条一批次，耗时：3.908s 开启BATCH：2.378s
//            sqlSession.commit();
//        } catch (Exception e) {
////            sqlSession.rollback();
//            logger.error("BaseServiceWorkTypeServiceImpl batchUpdateWorkTypes error{}", e);
//        } finally {
////            if (sqlSession != null) {
////                sqlSession.close();
////            }
//        }
        return 0;
    }

    @Override
    public int insertForeach() {

        long start = System.currentTimeMillis();
        List<SyncSysRoleFuncDTO> list = new ArrayList<>();
        Date currentDate = new Date();
        AtomicInteger saveCnt = new AtomicInteger();
        for (int i = 1; i < 100000; i++) {
            saveCnt.getAndIncrement();
            SyncSysRoleFuncDTO dept = new SyncSysRoleFuncDTO();
            dept.settSysFuncId(i);
            dept.settSysRoleId(i);
            dept.setCreateTime(currentDate);
            dept.setUpdateTime(currentDate);
            list.add(dept);
            if (saveCnt.get() % 100 == 0) {
                syncSysRoleFuncDAO.insertBatch(list);
                list.clear();
            }
        }
        syncSysRoleFuncDAO.insertBatch(list);
        long end = System.currentTimeMillis();
        logger.info("耗时:" + (end - start));
        // 10w:
        // 1一批次，耗时：516.271s
        // 100一批次，耗时：14.270s 20.912s 18.384s
        // 500一批次，耗时：17.813s 17.366s
        return 0;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(i%10);
        }
    }

    public static Integer getCapacity(ArrayList list) {
        Integer length = null;
        Class c = list.getClass();
        Field f;
        try {
            f = c.getDeclaredField("elementData");
            f.setAccessible(true);
            Object[] o = (Object[])f.get(list);
            length = o.length;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return length;
    }

    @Override
    public int deleteRoleFunc() {
        long start = System.currentTimeMillis();
        syncSysRoleFuncDAO.deleteAll();
        long end = System.currentTimeMillis();
        System.out.println("耗时:" + (end - start));
        return 0;
    }
}
