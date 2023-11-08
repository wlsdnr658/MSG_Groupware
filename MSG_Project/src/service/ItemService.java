package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import dao.CaritemDao;
import dao.ItemDao;
import dao.RoomitemDao;

@Service
public class ItemService {

   @Autowired
   private ItemDao dao;
   @Autowired
   private CaritemDao cardao;
   @Autowired
   private RoomitemDao roomdao;

//   private static final String UPLOAD_PATH = "/usr/local/itemImg/";
   private static final String UPLOAD_PATH = "C:\\itemImg";

   private static final int NUM_OF_CAR_PER_PAGE = 4;
   // 한번에 표시될 네비게이션의 개수 
   private static final int NUM_OF_NAVI_PAGE = 4;
   
   
   
   public Map<String, Object> mainitemList(){
      List<Map<String, Object>> maintitemList = dao.mainselectAll();
      Map<String, Object> result = new HashMap<String,Object>();
      
      result.put("maintitemList", maintitemList);
      
 
      
      return result;
   }

   
   public Map<String, Object> itemList() {

      List<Map<String, Object>> itemList = dao.selectAll();

      Map<String, Object> result = new HashMap<String, Object>();

      result.put("itemList", itemList);

      return result;
   }
   
   public List<Map<String, Object>> itemList1() {

      List<Map<String, Object>> itemList = dao.selectAll();

      

      return itemList;
   }

   public Map<String, Object> getitemList(Map<String, Object> param, int page) {

      List<Map<String, Object>> itemList;

      int pageTotalCount;
      int startPage = getStartPage(page);
      int endPage = getEndPage(page);
      int firstRow = getFirstRow(page);
      int endRow = getEndRow(page);

      param.put("firstRow", firstRow);
      param.put("endRow", endRow);

      int type = (int) param.get("type");
      String keyword = (String) param.get("keyword");

      if (type == 1) {
         param.put("typeName", keyword);

      } else if (type == 2) {
         param.put("itemNo", keyword);
      }

      pageTotalCount = getPageTotalCount(dao.totalItemCount(param));

      itemList = dao.searchItem(param);

      Map<String, Object> result = new HashMap<String, Object>();

      result.put("itemList", itemList);
      result.put("startPage", startPage);
      result.put("endPage", endPage);
      result.put("currentPage", page);
      result.put("pageTotalCount", pageTotalCount);

      return result;
   }

   private int getPageTotalCount(int totalCount) {
      int pageTotalCount = 0;
      if (totalCount != 0) {
         pageTotalCount = (int) Math.ceil(((double) totalCount / NUM_OF_CAR_PER_PAGE));
      }
      return pageTotalCount;
   }

   private int getStartPage(int currentPage) {
      return ((currentPage - 1) / NUM_OF_NAVI_PAGE) * NUM_OF_NAVI_PAGE + 1;
   }

   private int getEndPage(int currentPage) {
      return (((currentPage - 1) / NUM_OF_NAVI_PAGE) + 1) * NUM_OF_NAVI_PAGE;
   }

   private int getFirstRow(int currentPage) {
      return (currentPage - 1) * NUM_OF_CAR_PER_PAGE + 1;
   }

   private int getEndRow(int currentPage) {
      return currentPage * NUM_OF_CAR_PER_PAGE;
   }

   private String writeFile(MultipartFile fileName) {
 

      String fName = null;
      //UUID uuid = UUID.randomUUID();
      //fName = uuid.toString() + "_" + fileName.getOriginalFilename();
      fName = fileName.getOriginalFilename();

      File target = new File(UPLOAD_PATH, fName);
      try {
         FileCopyUtils.copy(fileName.getBytes(), target);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return fName;
   }

   public byte[] getImageAsByteArray(String typeName) {

      String fileName = dao.getFileName(typeName);

      if (fileName == null) {

         fileName = "default.jpg";

      } else if (fileName.equals("소형")) {

         fileName = "moning.jpg";

      }else if (fileName.equals("소형1")) {

          fileName = "martize.jpg";

       } else if (fileName.equals("중형")) {

         fileName = "sonata.jpg";

      } else if (fileName.equals("소회의실")) {

         fileName = "so.jpg";

      } else if (fileName.equals("중회의실")) {

         fileName = "medie.jpg";

      } else if (fileName.equals("대회의실")) {

         fileName = "bigroom.jpg";

      }else if (fileName.equals("회의실")) {

         fileName = "progectroom.jpg";

      }

      File originFile = new File(UPLOAD_PATH + "/" + fileName);

      InputStream targetStream = null;

      try {
         targetStream = new FileInputStream(originFile);

         return IOUtils.toByteArray(targetStream);
      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return null;
   }

   public Map<String, Object> itemsList() {

      List<Map<String, Object>> itemsList = dao.itemsAll();

      Map<String, Object> result = new HashMap<String, Object>();

      result.put("itemsList", itemsList);

      return result;
   }

   public boolean additem(Map<String, Object> item, MultipartFile fileName) {

      try {
         dao.insertitem(item);

         String fullName = writeFile(fileName);

         Map<String, Object> params = new HashMap<String, Object>();
         params.put("FULLNAME", fullName);
         params.put("NUM", item.get("itemNo"));
         dao.insertuploaditem(params);

         return true;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   public boolean removeitem(Map<String, Object> item) {
      int result = dao.updateitemstatus(item);

 

      int number = Integer.parseInt(item.get("itemNo").toString());

 

      if (result > 0) {
 

         if (number == 1) {

 

            cardao.updatestatusCaritem(item);
            return true;
         } else if (number == 2) {

 
            roomdao.updatstatusRoomitem(item);
            return true;
         }

         return true;

      } else {
         return false;
      }

   }

   public boolean modifyitem(Map<String, Object> item) {
      if (dao.updateitem(item) > 0) {
         return true;
      } else {
         return false;
      }
   }

}
