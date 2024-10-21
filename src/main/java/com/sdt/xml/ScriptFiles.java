/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.xml;

import com.sdt.data.BallBean;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Srikanth
 */
public class ScriptFiles {
    public static HashMap<String, ArrayList<BallBean>> script_map = new HashMap<>();
    public static void loadScripts(){
        if(HandleFile.script_dir==null)
            HandleFile.script_dir = HandleFile.workingDir+"/Media/script/";
        File script_folder = new File(HandleFile.script_dir);
        if(!(script_folder).exists()){
            script_folder.mkdirs();
            for(int i=0;i<5;i++){
                createDefaultFile("Default L"+(i+1));
                createDefaultFile("Default R"+(i+1));
            }
        }
        for(int i=0;i<5;i++){
            readFile("Default L"+(i+1));
            readFile("Default R"+(i+1));
        }
    }
    
    public static void readFile(String filename){
        SAXBuilder saxBuilder = new SAXBuilder();
        Element root = null;
        try {
            String file_name = HandleFile.script_dir+"/"+filename+".xml";
            File file = new File(file_name);
            if(!file.exists()||!file.isFile())
                return;
            Document document = saxBuilder.build(file);
            root = document.getRootElement();
            Element ballinfo = root.getChild("BallInfo");
            if(ballinfo!=null){
                List<Element> eList1 = ballinfo.getChildren("BallData");
                ArrayList<BallBean> ball_list = new ArrayList<>();
                for (int i = 0; i < eList1.size(); i++) {
                    Element balldata = eList1.get(i);
                    BallBean ballBean = new BallBean();
                    ballBean.setIndex(Integer.parseInt(balldata.getChildText("index")));
                    ballBean.setBowler_path(balldata.getChildText("bowler_path"));
                    ballBean.setBall_release(Integer.parseInt(balldata.getChildText("ball_release")));
                    ballBean.setBall_speed_1(Integer.parseInt(balldata.getChildText("ball_speed_1")));
                    ballBean.setBall_speed_2(Integer.parseInt(balldata.getChildText("ball_speed_2")));
                    ballBean.setBall_speed_3(Integer.parseInt(balldata.getChildText("ball_speed_3")));
                    ballBean.setBall_speed_4(Integer.parseInt(balldata.getChildText("ball_speed_4")));
                    ballBean.setTilt(Integer.parseInt(balldata.getChildText("tilt")));
                    ballBean.setPan(Integer.parseInt(balldata.getChildText("pan")));
                    ballBean.setLeft(Integer.parseInt(balldata.getChildText("left")));
                    ballBean.setRight(Integer.parseInt(balldata.getChildText("right")));
                    ball_list.add(ballBean);
                }
                script_map.put(filename, ball_list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void createDefaultFile(String filename ){
        try {
            Element root=new Element("MainData");
            Document doc=new Document();
            doc.setRootElement(root);
            Element ballinfo = new Element("BallInfo");
            root.addContent(ballinfo);
            BallBean ballBean = new BallBean();
            Element balldata = new Element("BallData");            
            ballinfo.addContent(balldata);
            balldata.addContent(new Element("index").addContent(ballBean.getIndex()+""));
            balldata.addContent(new Element("bowler_path").addContent(ballBean.getBowler_path()));
            balldata.addContent(new Element("ball_release").addContent(ballBean.getBall_release()+""));
            balldata.addContent(new Element("ball_speed_1").addContent(ballBean.getBall_speed_1()+""));
            balldata.addContent(new Element("ball_speed_2").addContent(ballBean.getBall_speed_2()+""));
            balldata.addContent(new Element("ball_speed_3").addContent(ballBean.getBall_speed_3()+""));
            balldata.addContent(new Element("ball_speed_4").addContent(ballBean.getBall_speed_4()+""));
            balldata.addContent(new Element("tilt").addContent(ballBean.getTilt()+""));
            balldata.addContent(new Element("pan").addContent(ballBean.getPan()+""));
            balldata.addContent(new Element("left").addContent(ballBean.getLeft()+""));
            balldata.addContent(new Element("right").addContent(ballBean.getRight()+""));
            
            balldata = new Element("BallData");            
            ballinfo.addContent(balldata);
            balldata.addContent(new Element("index").addContent(ballBean.getIndex()+1+""));
            balldata.addContent(new Element("bowler_path").addContent(ballBean.getBowler_path()));
            balldata.addContent(new Element("ball_release").addContent(ballBean.getBall_release()+""));
            balldata.addContent(new Element("ball_speed_1").addContent(ballBean.getBall_speed_1()+""));
            balldata.addContent(new Element("ball_speed_2").addContent(ballBean.getBall_speed_2()+""));
            balldata.addContent(new Element("ball_speed_3").addContent(ballBean.getBall_speed_3()+""));
            balldata.addContent(new Element("ball_speed_4").addContent(ballBean.getBall_speed_4()+""));
            balldata.addContent(new Element("tilt").addContent(ballBean.getTilt()+""));
            balldata.addContent(new Element("pan").addContent(ballBean.getPan()+""));
            balldata.addContent(new Element("left").addContent(ballBean.getLeft()+""));
            balldata.addContent(new Element("right").addContent(ballBean.getRight()+""));
            
            XMLOutputter outter=new XMLOutputter();
            outter.setFormat(Format.getPrettyFormat());
            //System.out.println(parentFolder+filename.getText()+".xml");
            FileOutputStream mdata = new FileOutputStream(new File(HandleFile.script_dir+"/"+filename+".xml"));
            outter.output(doc, mdata);   
            mdata.close();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
