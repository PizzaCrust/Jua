grammar Jua;

Q_NAME: [a-zA-Z][a-zA-Z0-9]*;
STRING: '"' ~('"')* '"';
INTEGER: [0-9];
DOUBLE: INTEGER '.' (INTEGER)*;

primitive
                    :
                    | STRING
                    | DOUBLE
                    | INTEGER
                    | Q_NAME
                    ;

expression
                    :
                    | primitive
                    | invocation
                    | staticInvocation
                    | staticVarReference
                    ;

args
                    :
                    | expression (',' expression)*
                    ;

invocation          : Q_NAME '(' args ')'
                    ;

staticInvocation    : Q_NAME '.' Q_NAME '(' args ')'
                    ;

varDeclare          :
                    | 'var' Q_NAME
                    | 'var' Q_NAME '=' expression
                    ;

potentialAssignationNames:
                         | Q_NAME
                         | staticVarReference
                         ;

varAssign
                    : potentialAssignationNames '=' expression
                    ;

staticVarReference
                    : Q_NAME '.' Q_NAME
                    ;

bodyExpression
                    :
                    | staticInvocation
                    | invocation
                    | varDeclare
                    | varAssign
                    ;

body
                    :
                    | bodyExpression (';' bodyExpression)*
                    ;

functionArgs
                    :
                    | Q_NAME (',' Q_NAME)*
                    ;

function
                    : 'func' Q_NAME '(' functionArgs ')' '{' body '}'
                    ;

classBodyExpr
                    :
                    | varDeclare
                    | constructor
                    | function
                    ;

superInvocation
                    : 'super(' args ')'
                    ;

constructorBody
                    : superInvocation body
                    ;

constructor
                    : 'constructor(' functionArgs ')' '{' constructorBody '}'
                    ;

classBody
                    :
                    | classBodyExpr (';' classBodyExpr)*
                    ;

class_declare
                    :
                    | 'class' Q_NAME '{' classBody '}'
                    | 'class' Q_NAME ':' Q_NAME (',' Q_NAME)* '{' classBody '}'
                    ;

WS: [ \t\u000C\r\n]+ -> skip;