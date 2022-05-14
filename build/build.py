import shutil
from coxbuild.schema import task, group, named, run, depend, withConfig, Configuration, ext
from pathlib import Path
from build.utils import root, prun, projects

buildGroup = group("build")


@buildGroup
@withConfig
@task
def project(config: Configuration):
    def onebuild(name: str):
        subdir = root / name
        print(f"Building {subdir.name}")
        prun(["gradle", "build"], cwd=subdir)

    name: str = config.get("name") or ""
    if not name:
        for project in projects():
            onebuild(project)
    else:
        onebuild(name)


@buildGroup
@withConfig
@task
def image(config: Configuration):
    def onebuild(name: str):
        subdir = root / name
        print(f"Building {subdir.name}")
        prun(["gradle", "bootBuildImage", f"--imageName=micropos/{name}"], cwd=subdir)

    name: str = config.get("name") or ""
    if not name:
        for project in projects():
            onebuild(project)
    else:
        onebuild(name)
