from pathlib import Path
import os
from platform import system
from coxbuild.schema import task, group, named, run as innerRun, depend, ext, withExecutionState, ExecutionState, withProject, ProjectSettings, withConfig, Configuration


root = Path(".")


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
def default(config: Configuration):
    name: str = config.get("name") or ""
    if not name:
        for subdir in root.iterdir():
            if subdir.is_dir() and (subdir / "build.gradle").exists():
                print(f"Compiling {subdir.name}")
                prun(["gradle", "build"], cwd=subdir)
    else:
        prun(["gradle", "build"], cwd=root / name)
