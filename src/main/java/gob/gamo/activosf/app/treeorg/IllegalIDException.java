package gob.gamo.activosf.app.treeorg;
// If given id is not present in the organization 
// Performing operations which are not allowed (e.g. deleting root)
public class IllegalIDException extends RuntimeException{  
    IllegalIDException(String s){  
        super(s);  
    }  
}
