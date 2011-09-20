package openmessenger

import javax.swing.text.InternationalFormatter.ExtendedReplaceHolder;

class GroupChat extends Event {
	String codename
	
    static constraints = {
		codename(size:1..7, nullable: false, unique:true)
    }
}
