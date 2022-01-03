import groovy.json.JsonOutput

def obj2Json(objectName)
{
  return JsonOutput.prettyPrint(JsonOutput.toJson(_context.getValue(objectName)))
}
