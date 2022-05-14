from pathlib import Path
import os
from platform import system
import shutil
from coxbuild.schema import task, group, named, run as innerRun, depend, ext, withExecutionState, ExecutionState, withProject, ProjectSettings, withConfig, Configuration


root = Path(".")


def projects():
    for subdir in root.iterdir():
        if subdir.is_dir() and (subdir / "build.gradle").exists():
            yield subdir.name


def prun(*args, **kwargs):
    innerRun(*args, **kwargs, shell=system() == "Windows")


def ensureDir(dir: Path):
    if dir.exists() and dir.is_dir():
        return
    os.makedirs(dir)


@withConfig
@task
def run(config: Configuration):
    name: str = config.get("name") or ""
    if not name:
        print("No name specified")
        return
    prun(["gradle", "bootRun"], cwd=root / name)


@withConfig
@task
def test(config: Configuration):
    def onetest(name: str):
        subdir = root / name
        print(f"Testing {subdir.name}")
        prun(["gradle", "test"], cwd=subdir)

    name: str = config.get("name") or ""
    if not name:
        for project in projects():
            onetest(project)
    else:
        onetest(name)


@withConfig
@task
def default(config: Configuration):
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


@depend(default, test)
@task
def ci():
    ciroot = root / "build" / "ci"
    ensureDir(ciroot)
    for project in projects():
        target = ciroot / project
        if target.exists():
            shutil.rmtree(target)
        shutil.copytree(root / project / "build", ciroot / project)
