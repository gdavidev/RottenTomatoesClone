package customJspComponents.formItems;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

public class TextFieldHandler extends TagSupport {
	private static final long serialVersionUID = 9182930244857991365L;
	private String name;
	private String label;
	private String placeholder;
	private String bottomTipText;
	private String value;
	private boolean required;
	
	public void setName(String name) {
		this.name = name;
	}
	public void setlabel(String label) {
		this.label = label;
	}
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	public void setBottomTipText(String bottomTipText) {
		this.bottomTipText = bottomTipText;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	
	public int doStartTag() throws JspException {
		boolean hasBottomTipText = this.bottomTipText != null;
		JspWriter out = pageContext.getOut();
		String elementTemplate =
			"  <div id='" + this.name + "Container' class='mb-3'>"
			+ "		<label for='" + this.name + "' class='form-label'>" + this.label + "</label>"
			+ "		<input type='text' class='form-control' " + (hasBottomTipText ? "aria-describedby='" + this.name + "Tip'" : "") 
			+ " 			id='" + this.name + "'"
			+ " 			name='" + this.name + "'"
			+ " 			placeholder='" + (this.placeholder != null ? this.placeholder : "") + "'"
			+ "				value='" + (this.value != null ? this.value : "") + "'"
			+ "				" + (this.required ? "required" : "") + ">"
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
