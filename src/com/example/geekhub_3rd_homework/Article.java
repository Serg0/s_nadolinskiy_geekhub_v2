package com.example.geekhub_3rd_homework;


public class Article {
	public Article(String OuterTitle, String OuterContent) {
		// TODO Auto-generated constructor stub
		title=OuterTitle;
		content=OuterContent;
	}
	String title;
	String content;


//public static void getConnection(){
//	
//	 Thread thread = new Thread() {
// 	    public void run() {
// 	    	try {
//                 
// 	    			URL url = new URL("http://android-developers.blogspot.com/feeds/posts/default?alt=json");
//              	 	HttpURLConnection con = (HttpURLConnection) url.openConnection();
//              		long i = 0; 
//                    readStream(con.getInputStream());
//              		//array = readStream(con.getInputStream());
//
//              }
//             	catch (Exception e) {
//                  	  e.printStackTrace();
//                  }
//
// 	    }
// 	 };
// 	 thread.start();
// 	 try {
// 		 thread.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//     
//	
//}
//
//
//private static ArrayList<Content> readStream(InputStream in) throws JSONException {
//	// TODO Auto-generated method stub
//	StringBuilder sb = new StringBuilder();
//	BufferedReader reader = null;
//	ArrayList<Content> localArray = new ArrayList<Content>();
//	  try {
//	    reader = new BufferedReader(new InputStreamReader(in));
//	    String line = "";
//
//	    while ((line = reader.readLine()) != null)
//	    {
//	        sb.append(line);
//	    }
//	    String result = sb.toString();
//	    
//	    
//	    JSONObject jObject = new JSONObject(result);
//	    JSONObject feed = jObject.getJSONObject("feed");
//	    JSONArray jArray = feed.getJSONArray("entry");
// 
//	    for (int i=0; i < jArray.length(); i++)
//	    {
//	        JSONObject oneObject = jArray.getJSONObject(i);
//	        JSONObject oneObjectsItem = oneObject.getJSONObject("title");
//	        String title = oneObjectsItem.getString("$t");
//	        JSONObject oneObjectsItem2 = oneObject.getJSONObject("content");
//  	        String content = oneObjectsItem2.getString("$t");
//	        localArray.add(new Content (title, content));
//	        
//	       
//	    }
//	  
//	    
//	  } catch (IOException e) {
//	    e.printStackTrace();
//	  } finally {
//	    if (reader != null) {
//	      try {
//	        reader.close();
//	      } catch (IOException e) {
//	        e.printStackTrace();
//	        }
//	    }
//	  }
//	  
//	  return localArray;
//
//}


}