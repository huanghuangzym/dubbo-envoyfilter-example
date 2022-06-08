package com.embedded;

/*
  for text/html files
import java.io.File;
import java.net.URISyntaxException;
import javax.ws.rs.Produces;
import javax.activation.MimetypesFileTypeMap;
*/
import org.apache.dubbo.samples.basic.api.DemoService;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import javax.ws.rs.core.Response;

import org.apache.dubbo.samples.basic.api.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Path("/")
public class Index {
    private ClassPathXmlApplicationContext context;

    /*
    for html files
    @GET
    @Produces("text/html")
    public Response  index() throws URISyntaxException {
        File f = new File(System.getProperty("user.dir")+"/index.html");
        String mt = new MimetypesFileTypeMap().getContentType(f);
        return Response.ok(f, mt).build();
    }
    */

    public Index() {
        context = new ClassPathXmlApplicationContext("spring/dubbo-demo-consumer.xml");
        context.start();
    }

    @GET
    @Path("/hello")
    public Response  echoGet() {
        try{
            DemoService demoService = (DemoService) context.getBean("demoService");
            String hello = demoService.sayHello("Test func");
            System.out.println(hello);
            return Response.status(200).entity(hello).build();
        }
        catch(Exception ex){
            ex.printStackTrace();
            return Response.status(503).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/abc")
    public Response  echoAbc() {
        try{
            DemoService demoService = (DemoService) context.getBean("demoService");
            String hello = demoService.sayAbc("Test Abc");
            System.out.println(hello);
            return Response.status(200).entity(hello).build();
        }
        catch(Exception ex){
            ex.printStackTrace();
            return Response.status(503).entity(ex.getMessage()).build();
        }
    }
}
