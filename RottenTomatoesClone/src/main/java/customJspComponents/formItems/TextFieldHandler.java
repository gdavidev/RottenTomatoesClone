package customJspComponents.formItems;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

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
			"  <div class='mb-3'>"
			+ "		<label for='" + this.name + "' class='form-label'>" + this.label + "</label>"
			+ "		<input type='text' class='form-control' " + (hasBottomTipText ? "aria-describedby='" + this.name + "Tip'" : "") 
			+ " 			id='" + this.name + "'"
			+ " 			name='" + this.name + "'"
			+ " 			placeholder='" + this.placeholder + "'"
			+ "				value='" + (this.value != null ? this.value == null : "") + "'"
			+ "				required='" + (this.required ? "true" : "false") + "'>"
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
