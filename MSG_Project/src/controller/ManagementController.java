package controller;

import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.*;

import service.CarItemService;
import service.ItemService;
import service.RoomItemService;

@Controller
@RequestMapping("/management")
public class ManagementController {

   @Autowired
   private ItemService itemservice;
   @Autowired
   private CarItemService carservice;
   @Autowired
   private RoomItemService roomservice;
   
   
   
   @RequestMapping("/main1")
   public String mainitemListForm(@RequestParam Map<String, Object> item, Model model) {
      
      
      
      
      Map<String, Object> mainitem = new HashMap<String, Object>();

      mainitem  = itemservice.mainitemList
            ();
      
      model.addAttribute("mainitem", mainitem);
      
 
      
      return "main1";
   }
   

   @RequestMapping(value = "/managementiteminsert", method = RequestMethod.GET)
   public String insertitemForm() {

      return "managementiteminsert";

   }

   
   @RequestMapping(value = "/insertitem", method = RequestMethod.POST)
   public String insertitem(@RequestParam Map<String, Object> item, MultipartFile fileName, Model model) {

      if (itemservice.additem(item, fileName)) {

         model.addAttribute("msg", "등록완료");
         
      } else {

         model.addAttribute("msg", "등록실패");
      }
      model.addAttribute("url", "managementitemcreate");
      
      return "menageresult";

   }

   @RequestMapping(value = "/deleteitem")
   public String removeitem(@RequestParam Map<String, Object> item, Model model) {

      if (itemservice.removeitem(item)) {
    	  
    	  model.addAttribute("typeName", item.get("typeName"));
         model.addAttribute("msg", "반납완료");

      } else {

         model.addAttribute("msg", "반납실패");
      }

      return "redirect:managementitemcreate";
   }

   @ResponseBody
   @RequestMapping(value = "/modifyitem")
   public boolean modifyitem(@RequestParam Map<String, Object> item) {

      boolean result = false;
      if (itemservice.modifyitem(item)) {
         result = true;
         
      } else {
         result = false;
      }

      return result;
   }

   @RequestMapping("/managementitemcreate")
   public String managementitemList(@RequestParam Map<String, Object> item, Model model, HttpServletRequest req) {
      
      req.setAttribute("viewitemData", itemservice.getImageAsByteArray((String) item.get("UID")));

      Map<String, Object> viewitemData = new HashMap<String, Object>();

      viewitemData = itemservice.itemList();

      model.addAttribute("viewitemData", viewitemData);

      return "managementitemcreate";
   }

   // 페이징 이다 임마
   @RequestMapping(value = "/managementitemcreate", method = RequestMethod.GET)
   public String managementitemList(Model model, @RequestParam(required = false) String keyword,
         @RequestParam(defaultValue = "0") int type, @RequestParam(defaultValue = "1") int page) {

      
      

      Map<String, Object> param = new HashMap<String, Object>();
      param.put("keyword", keyword);
      param.put("type", type);

      Map<String, Object> viewitemList = itemservice.getitemList(param, page);
      viewitemList.put("type", type);
      viewitemList.put("keyword", keyword);

      

      model.addAttribute("viewitemList", viewitemList);

      return "managementitemcreate";
   }

   // 공통
   @ResponseBody
   @RequestMapping("/getItemImage")
   public byte[] getItemImg(String typeName) {

      
      

      return itemservice.getImageAsByteArray(typeName);
   }

   // -------------------------------------------------------------------

   @RequestMapping("/managementcalendar")
   public String managementfullcalendar() {

      return "managementcalendar";
   }

   @RequestMapping("/calendar")
   public String managementcalendar() {



      return "calendar";
   }

   @RequestMapping("/managementCar")
   public String managementCarList(@RequestParam Map<String, Object> caritem, Model model, HttpServletRequest req) {

      
      req.setAttribute("viewData", itemservice.getImageAsByteArray((String) caritem.get("UID")));

      Map<String, Object> viewData = new HashMap<String, Object>();

      viewData = itemservice.itemList();

      

      

      model.addAttribute("viewData", viewData);

      return "managementCar";
   }
   
   @RequestMapping(value = "/managementCarform")
   public String managementCarReserVationForm(@RequestParam Map<String, Object> caritem, Model model) {
      
      model.addAttribute("typeName", caritem.get("typeName"));
      model.addAttribute("listName", caritem.get("listName"));
//
      model.addAttribute("start", caritem.get("start"));
      model.addAttribute("end", caritem.get("end"));

      

      return "managementCarreservation";
   }
   
   @ResponseBody
   @RequestMapping(value = "/managementCarreservation", method = RequestMethod.GET)
   public boolean managementCarReserVationAction(@RequestParam Map<String, Object> caritem, Model model) {

      System.out.println("caritem" + caritem);
      if (carservice.dateCarCheck(caritem)) {
         
         return true;
      }else {
         return false;
      }

   }

   @ResponseBody
   @RequestMapping(value = "/managementCarreservation", method = RequestMethod.POST)
   public Boolean managementCarReserVation(@RequestParam Map<String, Object> caritem) {
      /*
       * Date start = (Date)caritem.get("startDate"); Date end =
       * (Date)caritem.get("endDate");
       */

      

      boolean result = false;

      // boolean chk = (carservice.dateCheck(caritem) != null);

      if (carservice.dateCarCheck(caritem)) {

         carservice.insertcaritem(caritem);

         result = true;
         
      } else {
         result = false;

      }
      

      return result;

   }


   @ResponseBody
   @RequestMapping(value = "/managementCarmodifyList", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
   public String managementCalendarCaritem(HttpServletResponse response, ServletResponse request,String typeName) throws IOException {

     
	   System.out.println("asd"+ typeName);
      Map<String, Object> viewcarData = new HashMap<String, Object>();

      viewcarData = carservice.caritemList(typeName);
      
  
     

      String json = new Gson().toJson(viewcarData);

     

      response.setHeader("Content-Type", "application/xml");
      response.setContentType("text/xml;charset=UTF-8");
      response.setCharacterEncoding("utf-8");

      return json;
   }
   
   @ResponseBody
   @RequestMapping(value = "/managementitemsList", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
   public String managementCalendaritemss(HttpServletResponse response, ServletResponse request) throws IOException {

     

	   
      Map<String, Object> viewitemsData = new HashMap<String, Object>();

      viewitemsData = itemservice.itemsList();

     

      String json = new Gson().toJson(viewitemsData);

     

      response.setHeader("Content-Type", "application/xml");
      response.setContentType("text/xml;charset=UTF-8");
      response.setCharacterEncoding("utf-8");

      return json;
   }
   
   

   @ResponseBody
   @RequestMapping(value = "/managementitemsList", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
   public String managementCalendaritems(HttpServletResponse response, ServletResponse request) throws IOException {

     

      Map<String, Object> viewitemsData = new HashMap<String, Object>();

      viewitemsData = itemservice.itemsList();

     

      String json = new Gson().toJson(viewitemsData);

     

      response.setHeader("Content-Type", "application/xml");
      response.setContentType("text/xml;charset=UTF-8");
      response.setCharacterEncoding("utf-8");

      return json;
   }

   // 페이징 이다 임마
   @RequestMapping(value = "/managementCarmodify", method = RequestMethod.GET)
   public String managementCaritemList(Model model, @RequestParam(required = false) String keyword,
         @RequestParam(defaultValue = "0") int type, @RequestParam(defaultValue = "1") int page, String typeName) {
	   
	
	

      Map<String, Object> param = new HashMap<String, Object>();
      
      param.put("keyword", keyword);
      param.put("type", type);
      param.put("typeName", typeName);
    

      Map<String, Object> viewcarData = carservice.getCarItemList(param, page);
      viewcarData.put("type", type);
      viewcarData.put("keyword", keyword);

    

      model.addAttribute("viewcarData", viewcarData);

      return "managementCarmodify";
   }

   @RequestMapping(value = "/modify")
   public String carmodify(@RequestParam Map<String, Object> caritem, Model model) {

	   System.out.println("caritem" + caritem);
	   
      if (carservice.modifycaritem(caritem)) {
    	  model.addAttribute("msg", "수정성공");
         model.addAttribute("typeName", caritem.get("typeName"));
         
      } else {
    
         model.addAttribute("msg", "수정실패");
      }
      return "redirect:managementCarmodify";
   }

   @RequestMapping(value = "/delete")
   public String cardelete(@RequestParam Map<String, Object> caritem, Model model, HttpSession session) {
    

      Map<String, Object> tmp = (Map<String, Object>) session.getAttribute("user");

      String empNo = (String) tmp.get("empNo");


      

      caritem.put("assigned", tmp.get("empNo"));

      if (carservice.removecaritem(caritem)) {

    	  model.addAttribute("typeName",caritem.get("typeName"));
         model.addAttribute("msg", "반납완료");
         

      } else {

         model.addAttribute("msg", "반납실패");
      }

      return "redirect:managementCarmodify";
   }

   /*
    * ROOM-------------------------------------------------------------------------
    * -----------------
    */

   @RequestMapping("/calendar2")
   public String managementcalendar2() {

      return "calendar2";
   }

   @RequestMapping(value = "/managementRoom")
   public String managementRoomList(@RequestParam Map<String, Object> roomitem, Model model, HttpServletRequest req) {

      req.setAttribute("viewroomData", itemservice.getImageAsByteArray((String) roomitem.get("UID")));

      Map<String, Object> viewroomData = new HashMap<String, Object>();

      viewroomData = itemservice.itemList();



      model.addAttribute("viewroomData", viewroomData);

      return "managementRoom";
   }

   
   @RequestMapping(value = "/managementRoomreservform")
   public String managementRoomReserVationForm(@RequestParam Map<String, Object> roomitem, Model model) {

      model.addAttribute("start", roomitem.get("start"));
      model.addAttribute("end", roomitem.get("end"));
      model.addAttribute("typeName", roomitem.get("typeName"));
      model.addAttribute("listName", roomitem.get("listName"));

      return "managementRoomreservation";
   }
   
   @ResponseBody
   @RequestMapping(value = "/managementRoomreservation", method = RequestMethod.GET)
   public boolean managementRoomReserVationAction(@RequestParam Map<String, Object> roomitem, Model model) {

      if(roomservice.dateRoomCheck(roomitem)) {
         return true;
      }else {
         return false;
      }
   }

   @ResponseBody
   @RequestMapping(value = "/managementRoomreservation", method = RequestMethod.POST)
   public Boolean managementRoomReserVation(@RequestParam Map<String, Object> roomitem) {



      boolean result = false;

      if (roomservice.dateRoomCheck(roomitem)) {

         roomservice.insertroomitem(roomitem);

         result = true;

      } else {

         result = false;

      }


      return result;
   }

   @ResponseBody
   @RequestMapping(value = "/managementRoommodify", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
   public String managementCalendarRoomitem(HttpServletResponse response, ServletResponse request,String typeName) throws IOException {



      Map<String, Object> viewroomData = new HashMap<String, Object>();

      viewroomData = roomservice.roomitemList(typeName);



      String json = new Gson().toJson(viewroomData);



      response.setHeader("Content-Type", "application/xml");
      response.setContentType("text/xml;charset=UTF-8");
      response.setCharacterEncoding("utf-8");

      return json;
   }
   
   


   @RequestMapping(value = "/managementRoommodify", method = RequestMethod.GET)
   public String managementRoomitemList(Model model, @RequestParam(required = false) String keyword,
         @RequestParam(defaultValue = "0") int type, @RequestParam(defaultValue = "1") int page,String typeName) {

      Map<String, Object> param = new HashMap<String, Object>();
      param.put("keyword", keyword);
      param.put("type", type);
      param.put("typeName", typeName);

      Map<String, Object> viewroomData = roomservice.getRoomItemList(param, page);
      viewroomData.put("type", type);
      viewroomData.put("keyword", keyword);



      model.addAttribute("viewroomData", viewroomData);

      return "managementRoommodify";
   }

   @RequestMapping(value = "/Roommodify")
   public String Roommodify(@RequestParam Map<String, Object> roomitem, Model model) {


	   	
      if (roomservice.modifyroomitem(roomitem)) {
    	  
    	  
         model.addAttribute("msg", "수정완료");
         model.addAttribute("typeName", roomitem.get("typeName"));
         
      } else {

         model.addAttribute("msg", "수정실패");
      }
      
      return "redirect:managementRoommodify";
   }

   @RequestMapping(value = "/Roomdelete")
   public String roomdelete(@RequestParam Map<String, Object> roomitem, Model model, HttpSession session) {

      // int status = 0;
      // caritem.put("status", status);

      Map<String, Object> tmp = (Map<String, Object>) session.getAttribute("user");

      String empNo = (String) tmp.get("empNo");




      roomitem.put("assigned", tmp.get("empNo"));

      if (roomservice.removeroomitem(roomitem)) {

    	  model.addAttribute("typeName",roomitem.get("typeName"));
         model.addAttribute("msg", "반납완료");

      } else {

         model.addAttribute("msg", "반납실패");
      }

      return "redirect:managementRoommodify";
   }

}