package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Model;
import java.util.List;

public class AddInterfaceRequest {
    private Model model;
    private String interfaceName;
    private String packageName;
    private String visibility = "public";
    private List<OperationSpec> operations;
    
    public Model getModel() { return model; }
    public void setModel(Model model) { this.model = model; }
    public String getInterfaceName() { return interfaceName; }
    public void setInterfaceName(String interfaceName) { this.interfaceName = interfaceName; }
    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }
    public List<OperationSpec> getOperations() { return operations; }
    public void setOperations(List<OperationSpec> operations) { this.operations = operations; }
}