package customJspComponents.formItems;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

public class FileInputHandler extends TagSupport {
	private static final long serialVersionUID = 3851113150206505056L;
	private String name;
	private String label;
	private String accept;
	private boolean multiple;
	private String bottomTipText;
	private boolean required;
	
	public void setName(String name) {
		this.name = name;
	}
	public void setlabel(String label) {
		this.label = label;
	}
	public void setAccept(String accept) {
		this.accept = accept;
	}
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	public void setBottomTipText(String bottomTipText) {
		this.bottomTipText = bottomTipText;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	
	public int doStartTag() throws JspException {
		boolean hasBottomTipText = this.bottomTipText != null;
		JspWriter out = pageContext.getOut();
		String elementTemplate =
			"  <div class='mb-3'>"
			+ "		<label for='" + this.name + "' class='form-label'>" + this.label + "</label>"
			+ "		<input type='file' class='form-control' " + (hasBottomTipText ? "aria-describedby='" + this.name + "Tip'" : "") 
			+ " 			id='" + this.name + "'"
			+ " 			name='" + this.name + "'"
			+ " 			accept='" + this.accept + "'"
			+ "				required='" + (this.required ? "true" : "false") + "'"
			+ "				multiple='" + (this.multiple ? "true" : "false") + "'>"
			+ (hasBottomTipText ? "<div id='"+ this.name + "Tip' class='form-text'>" + this.bottomTipText + "</div>" : "")
			+ "</div>";	    
		try {
			out.write(elementTemplate);
		} catch (Exception e) {
			System.out.println(e);
		}
		return SKIP_BODY;
	}
}
