package customJspComponents.formItems;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ComboBoxHandler extends TagSupport {
	private static final long serialVersionUID = 9182930244857991365L;
	private String name;
	private String label;
	private String[] dataSource;
	private String onChange;
	private int selectedIndex;
	private boolean required;
	
	public void setName(String name) {
		this.name = name;
	}
	public void setlabel(String label) {
		this.label = label;
	}
	public void setDataSource(String dataSource) {
		String[] data = dataSource.split(",");
		for (int i = 0; i < data.length; i++)
			data[i] = data[i].trim();
		this.dataSource = data;
	}
	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	
	public int doStartTag() throws JspException {
		boolean hasOnChange = this.onChange != null;
		JspWriter out = pageContext.getOut();
		String elementTemplate =
			"  <div class='mb-3'>"
			+ "		<label for='" + this.name + "' class='form-label'>" + this.label + "</label>"
			+ "		<select class='form-select' "
			+ " 			id='" + this.name + "'"
			+ " 			name='" + this.name + "'"
			+ "				onchange=\"" + getUpdateSelectedOptionOnChange() + (hasOnChange ? this.onChange : "") + "\">";		
			
		elementTemplate += getOptionsTemplateElement() + "</select></div>";
		try {
			out.write(elementTemplate);
		} catch (Exception e) {
			System.out.println(e);
		}
		return SKIP_BODY;
	}
	
	private String getOptionsTemplateElement() {
		String elementOptionsTemplate = "";
		for (int i = 0; i < this.dataSource.length; i++)
			elementOptionsTemplate +=
				"  <option value='" + i + "' " + (this.selectedIndex == i ? "selected" : "") + ">"
				+ 		this.dataSource[i].toString() 
				+ "</option>";
		return elementOptionsTemplate;
	}
	
	private String getUpdateSelectedOptionOnChange() {
		return "$('option[value=' + this.value + ']', this).attr('selected', true).siblings().removeAttr('selected');";
	}
}