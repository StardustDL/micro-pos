from pathlib import Path
import os
from platform import system
import shutil
import sys
from coxbuild.schema import task, group, named, run as innerRun, depend, ext, withExecutionState, ExecutionState, withProject, ProjectSettings, withConfig, Configuration

if True:
    sys.path.append(str(Path(".").resolve()))
    from build.utils import root, prun, ensureDir, projects

build = ext("file://build/build.py").module
test = ext("file://build/test.py").module


@withConfig
@task
def run(config: Configuration):
    name: str = config.get("name") or ""
    if not name:
        print("No name specified")
        return
    prun(["gradle", "bootRun"], cwd=root / name)


@depend(build.project)
@task
def default(): pass


@depend(build.project, test.project, build.image)
@task
def ci():
    ciroot = root / "dist" / "ci"
    ensureDir(ciroot)
    for project in projects():
        target = ciroot / project
        if target.exists():
            shutil.rmtree(target)
        shutil.copytree(root / project / "build", ciroot / project)
